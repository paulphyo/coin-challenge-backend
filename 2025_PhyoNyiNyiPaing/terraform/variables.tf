variable "public_key" {
  type = string
  description = "The public key for the AWS key pair"
}

variable "aws_region" {
  description = "AWS region"
  default     = "ap-southeast-1"
}

variable "instance_type" {
  description = "EC2 instance type"
  default     = "t2.micro"
}

variable "key_name" {
  description = "Name of the existing EC2 key pair"
  default = "deployer_key"
}

variable "s3_bucket_name" {
  description = "Name of the S3 bucket for remote state"
  default = "coin-backend-s3"
}
