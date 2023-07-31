variable "aws_region" {
  default = "us-west-2"
}

variable "environment_to_suffix_map" {
  type = map(any)
  default = {
    dev     = "-dev"
    staging = "-stg"
    prod    = ""
  }
}
