resource "aws_alb" "statistics_server_load_balancer" {
  name            = "statistics-server-alb-${terraform.workspace}"
  subnets         = [aws_default_subnet.default_az1.id, aws_default_subnet.default_az2.id]
  security_groups = [aws_security_group.http_security_group.id, aws_security_group.allow_tomcat.id]
  idle_timeout    = 300
}

resource "aws_alb_target_group" "statistics_server_target_group" {
  name_prefix = "stattg"
  port        = var.default_tomcat_port
  protocol    = "HTTP"
  vpc_id      = aws_default_vpc.default.id
  target_type = "ip"

  lifecycle {
    create_before_destroy = true
  }

  health_check {
    healthy_threshold   = "3"
    interval            = "30"
    protocol            = "HTTP"
    matcher             = "200"
    timeout             = "3"
    path                = "/actuator/health"
    unhealthy_threshold = "2"
  }
}

resource "aws_alb_target_group" "statistics_server_target_group_blue_green" {
  name_prefix = "stttg2"
  port        = var.default_tomcat_port
  protocol    = "HTTP"
  vpc_id      = aws_default_vpc.default.id
  target_type = "ip"

  lifecycle {
    create_before_destroy = true
  }

  health_check {
    healthy_threshold   = "3"
    interval            = "30"
    protocol            = "HTTP"
    matcher             = "200"
    timeout             = "3"
    path                = "/actuator/health"
    unhealthy_threshold = "2"
  }
}

data "aws_acm_certificate" "statistics_server_cetificate" {
  domain   = "statistics-api.worldcubeassociation.org"
  statuses = ["ISSUED"]
}

resource "aws_alb_listener" "statistics_server_https_listener" {
  load_balancer_arn = aws_alb.statistics_server_load_balancer.arn
  port              = var.http_port
  protocol          = "HTTP"
  # ssl_policy        = "ELBSecurityPolicy-TLS13-1-2-2021-06"
  # certificate_arn   = data.aws_acm_certificate.statistics_server_cetificate.arn

  default_action {
    target_group_arn = aws_alb_target_group.statistics_server_target_group.id
    type             = "forward"
  }
}
