resource "aws_iam_role" "statistics_server_code_deploy_role" {
  name = "statistics-server-code-deploy-role-${terraform.workspace}"

  inline_policy {
    name = "statistics-inline-cd-${terraform.workspace}"
    policy = templatefile("${path.module}/templates/policies/code-deploy-statistics.json", {
      cluster_name = aws_ecs_cluster.statistics_server_cluster.name
      service_name = aws_ecs_service.statistics_server_service.name
    })
  }

  assume_role_policy = templatefile("${path.module}/templates/policies/assume-role-code-deploy.json", {})
}

resource "aws_iam_role_policy_attachment" "statistics_server_code_deploy_role" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole"
  role       = aws_iam_role.statistics_server_code_deploy_role.name
}

resource "aws_codedeploy_app" "statistics_server_code_deploy_app" {
  name             = "statistics-server-code-deploy-app-${terraform.workspace}"
  compute_platform = "ECS"
}

resource "aws_codedeploy_deployment_group" "statistics_server_code_deployment_group" {
  app_name               = aws_codedeploy_app.statistics_server_code_deploy_app.name
  deployment_config_name = "CodeDeployDefault.ECSAllAtOnce"
  deployment_group_name  = "statistics-server-code-deployment-group"
  service_role_arn       = aws_iam_role.statistics_server_code_deploy_role.arn

  auto_rollback_configuration {
    enabled = true
    events  = ["DEPLOYMENT_FAILURE"]
  }

  blue_green_deployment_config {
    deployment_ready_option {
      action_on_timeout = "CONTINUE_DEPLOYMENT"
    }

    terminate_blue_instances_on_deployment_success {
      action                           = "TERMINATE"
      termination_wait_time_in_minutes = 5
    }
  }

  deployment_style {
    deployment_option = "WITH_TRAFFIC_CONTROL"
    deployment_type   = "BLUE_GREEN"
  }

  ecs_service {
    cluster_name = aws_ecs_cluster.statistics_server_cluster.name
    service_name = aws_ecs_service.statistics_server_service.name
  }

  load_balancer_info {
    target_group_pair_info {
      prod_traffic_route {
        listener_arns = [aws_alb_listener.statistics_server_https_listener.arn]
      }

      target_group {
        name = aws_alb_target_group.statistics_server_target_group.name
      }

      target_group {
        name = aws_alb_target_group.statistics_server_target_group_blue_green.name
      }
    }
  }
}

resource "local_file" "statistics_app_spec" {
  content  = <<EOF
version: 0.0
Resources:
  - TargetService:
      Type: AWS::ECS::Service
      Properties:
        TaskDefinition: "${replace(aws_ecs_task_definition.statistics_server_task_definition.arn, "/:[0-9]+$/", "")}"
        LoadBalancerInfo:
          ContainerName: "${var.container_name}"
          ContainerPort: "${var.default_tomcat_port}"
        PlatformVersion: "LATEST"
EOF
  filename = "app-spec/statistics-spec-${terraform.workspace}.yaml"
}

resource "aws_s3_object" "api_app_spec_file" {
  bucket = var.bucket_name
  key    = local_file.statistics_app_spec.filename
  source = local_file.statistics_app_spec.filename

  etag = md5(local_file.statistics_app_spec.content)
}
