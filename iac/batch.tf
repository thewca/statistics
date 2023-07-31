resource "aws_iam_role" "ecs_instance_role" {
  name = "ecs-instance-role-${terraform.workspace}"

  assume_role_policy = templatefile("${path.module}/templates/policies/assume-role-ec2.json", {})
}

resource "aws_iam_instance_profile" "ecs_instance_role" {
  name = "ecs-instance-role-${terraform.workspace}"
  role = aws_iam_role.ecs_instance_role.name
}

resource "aws_iam_role" "statistics_cron_service_role" {
  name = "statistics-cron-service-role-${terraform.workspace}"

  assume_role_policy = templatefile("${path.module}/templates/policies/assume-role-batch.json", {})
}

resource "aws_iam_role_policy_attachment" "statistics_cron_service_role" {
  role       = aws_iam_role.statistics_cron_service_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSBatchServiceRole"
}

resource "aws_batch_compute_environment" "statistics_cron_compute_environment" {
  type                     = "MANAGED"
  compute_environment_name = "statistics-cron-compute-environment-${terraform.workspace}"
  state                    = "ENABLED"
  service_role             = aws_iam_role.statistics_cron_service_role.arn

  compute_resources {
    max_vcpus = 4

    security_group_ids = [aws_security_group.all_out.id]

    subnets = [
      aws_default_subnet.default_az1.id,
      aws_default_subnet.default_az2.id
    ]

    type = "FARGATE"
  }

  depends_on = [aws_iam_role_policy_attachment.statistics_cron_service_role]
}

resource "aws_batch_job_queue" "statistics_cron_job_queue" {
  name                 = "statistics-cron-job-queue-${terraform.workspace}"
  state                = "ENABLED"
  priority             = 1
  compute_environments = [aws_batch_compute_environment.statistics_cron_compute_environment.arn]
}

resource "aws_batch_job_definition" "statistics_cron_job_definition" {
  name                  = "statistics-cron-job-definition-${terraform.workspace}"
  type                  = "container"
  platform_capabilities = ["FARGATE"]

  container_properties = templatefile("./templates/batch/statistics_cron.json.tpl", {
    image           = "${aws_ecr_repository.statistics_cron.repository_url}:latest"
    statistics_port = "8080"
    db_port         = "3306"
    db_host         = aws_db_instance.dumped_db.address
    db_name         = "wca_development"
    db_username     = data.aws_ssm_parameter.dumped_db_write_user.value
    db_password     = data.aws_ssm_parameter.dumped_db_write_password.value
    execution_role  = aws_iam_role.ecs_task_execution_role.arn
  })
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name               = "ecs-task-execution-role-${terraform.workspace}"
  assume_role_policy = templatefile("${path.module}/templates/policies/assume-role-ecs.json", {})
}
