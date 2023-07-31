resource "aws_security_group" "all_out" {
  name        = "all-out-${terraform.workspace}}"
  description = "Allow HTTP"
  vpc_id      = aws_default_vpc.default.id

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}

resource "aws_security_group" "mysql_default_port" {
  name        = "default-mysql-${terraform.workspace}}"
  description = "Default mysql"
  vpc_id      = aws_default_vpc.default.id

  ingress {
    description = "HTTP"
    from_port   = var.default_mysql_port
    to_port     = var.default_mysql_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}
