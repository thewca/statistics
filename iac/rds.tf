resource "aws_db_instance" "dumped_db" {
  identifier             = "dumped-db-${terraform.workspace}"
  allocated_storage      = "100"
  engine                 = "mysql"
  engine_version         = "8.0.33"
  storage_type           = "gp2"
  instance_class         = "db.t4g.large"
  db_name                = "dumped_db"
  username               = data.aws_ssm_parameter.dumped_db_write_user.value
  password               = data.aws_ssm_parameter.dumped_db_write_password.value
  port                   = var.default_mysql_port
  parameter_group_name   = "default.mysql8.0"
  skip_final_snapshot    = true
  publicly_accessible    = true
  vpc_security_group_ids = [aws_security_group.mysql_default_port.id, aws_security_group.all_out.id]
  apply_immediately      = true
}

