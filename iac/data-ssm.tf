data "aws_ssm_parameter" "statistics_app_id" {
  name = "/config/${terraform.workspace}/statistics/app/id"
}

data "aws_ssm_parameter" "dumped_db_write_user" {
  name = "/config/${terraform.workspace}/statistics/dumped_db/write_user"
}

data "aws_ssm_parameter" "dumped_db_write_password" {
  name = "/config/${terraform.workspace}/statistics/dumped_db/write_password"
}

data "aws_ssm_parameter" "dumped_db_read_user" {
  name = "/config/${terraform.workspace}/statistics/dumped_db/read_user"
}

data "aws_ssm_parameter" "dumped_db_read_password" {
  name = "/config/${terraform.workspace}/statistics/dumped_db/read_password"
}
