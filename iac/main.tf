terraform {
  backend "s3" {
    bucket = "NON-EXISTING-BUCKET"
    key    = "statistics-standalone-iac"
    region = "us-west-2"
  }
}

variable "bucket_name" {
  default = "NON-EXISTING-BUCKET"
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

  default_tags {
    tags = {
      Reason = "statistics"
    }
  }
}

provider "aws" {
  region = var.aws_region
  alias  = "no_tags"
}
