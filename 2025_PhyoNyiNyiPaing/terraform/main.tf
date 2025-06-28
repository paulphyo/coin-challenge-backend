provider "aws" {
  region = "ap-southeast-1"
}

resource "aws_key_pair" "deployer_key" {
  key_name   = "deployer-key"
  public_key = file("~/downloads/coin_challenge.pub") 
}

resource "aws_security_group" "backend_sg" {
  name        = "backend-sg"
  description = "Allow SSH and app port"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Create EC2 instance
resource "aws_instance" "backend" {
  ami           = "ami-02c7683e4ca3ebf58" # Ubuntu 24.04 in ap-southeast-1 
  instance_type = "t2.micro"
  key_name      = aws_key_pair.deployer_key.key_name

  vpc_security_group_ids = [aws_security_group.backend_sg.id]

  user_data = <<-EOF
              #!/bin/bash
              apt update -y
              apt install docker.io -y
              systemctl start docker
              systemctl enable docker
              usermod -aG docker ubuntu
              EOF

  tags = {
    Name = "coin-challenge-backend"
  }
}

# Allocate and associate an Elastic IP
resource "aws_eip" "backend_ip" {
  instance = aws_instance.backend.id
}

output "ec2_public_ip" {
  value = aws_eip.backend_ip.public_ip
}
