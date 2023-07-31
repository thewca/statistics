resource "aws_iam_role" "ecs_instance_role" {
  name = "ecs-instance-role-${terraform.workspace}"

  assume_role_policy = templatefile("${path.module}/templates/policies/ecs-instance-role.json")
}
