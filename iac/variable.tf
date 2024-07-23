variable "aws_region" {
  default = "us-west-2"
}

variable "type" {
  default = "Type"
}

variable "type_subnet" {
  default = "Subnet"
}

variable "type_ecr" {
  default = "ECR"
}

variable "type_ssm" {
  default = "SSM"
}

variable "type_sg" {
  default = "SG"
}

variable "default_mysql_port" {
  default = 3306
}

variable "http_port" {
  default = 80
}

variable "https_port" {
  default = 443
}

variable "default_tomcat_port" {
  default = "8080"
}

variable "statistics_fargate_cpu" {
  default = "1024"
}

variable "statistics_fargate_memory" {
  default = "4096"
}

variable "dumped_db_name" {
  default = "wca_development"
}

variable "container_name" {
  default = "statistics-server-app"
}

variable "app_spec_folder" {
  default = "app-spec"

}
