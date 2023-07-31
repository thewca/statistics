terraform {
  backend "s3" {
    bucket = "NON-EXISTING-BUCKET"
    key    = "statistics-iac"
    region = "us-west-2"
  }
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}
