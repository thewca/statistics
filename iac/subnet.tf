resource "aws_default_subnet" "default_az1" {
  availability_zone = "${var.aws_region}a"

  tags = {
    (var.type) = var.type_subnet
  }
}

resource "aws_default_subnet" "default_az2" {
  availability_zone = "${var.aws_region}b"

  tags = {
    (var.type) = var.type_subnet
  }
}
