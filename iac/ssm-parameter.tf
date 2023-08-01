resource "aws_ssm_parameter" "statistics_server_app_ecr_url" {
  name  = "/${terraform.workspace}/ecr/url/statistics_server"
  type  = "String"
  value = aws_ecr_repository.statistics_server.repository_url

  tags = {
    "Type" = var.type_ssm
  }
}
