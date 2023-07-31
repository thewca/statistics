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
