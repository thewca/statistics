output "access_url" {
  value = aws_alb.statistics_server_load_balancer.dns_name
}