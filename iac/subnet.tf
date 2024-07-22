resource "aws_default_subnet" "default_az1" {
  availability_zone = "${var.aws_region}a"

  provider = aws.no_tags
}

resource "aws_default_subnet" "default_az2" {
  availability_zone = "${var.aws_region}b"

  provider = aws.no_tags
}
