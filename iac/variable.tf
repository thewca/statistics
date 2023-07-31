variable "environment_to_suffix_map" {
  type = map(any)
  default = {
    dev     = "-dev"
    staging = "-stg"
    prod    = ""
  }
}

variable "aws_region" {
  default = "us-west-2"
}

variable "type" {
  default = "Type"
}

variable "type_subnet" {
  default = "Subnet"
}
