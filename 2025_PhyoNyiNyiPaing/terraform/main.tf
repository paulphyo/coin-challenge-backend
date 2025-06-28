provider "aws" {
  region = var.aws_region
}

terraform {
  backend "s3" {
    bucket = var.s3_bucket_name
    key    = "terraform/terraform.tfstate"
    region = var.aws_region
    encrypt = true
  }
}
resource "aws_instance" "backend" {
  ami           = "ami-02c7683e4ca3ebf58"  # Ubuntu 24.04 in ap-southeast-1
  instance_type = var.instance_type
  key_name      = var.key_name

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
  
  lifecycle {
    create_before_destroy = true
  }
}

# Allocate and associate an Elastic IP
resource "aws_eip" "backend_ip" {
  instance = aws_instance.backend.id
}

output "ec2_public_ip" {
  value = aws_eip.backend_ip.public_ip
}
