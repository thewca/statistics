resource "aws_ecr_repository" "statistics_cron" {
  name = "statistics-cron-${terraform.workspace}"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    (var.type) = var.type_ecr
  }
}

resource "aws_ecr_lifecycle_policy" "expire_images_statistics_cron" {
  repository = aws_ecr_repository.statistics_cron.name

  policy = templatefile("./templates/ecr/expire-policy.json", {})
}

resource "aws_ecr_repository" "statistics_server" {
  name = "statistics-server-${terraform.workspace}"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    (var.type) = var.type_ecr
  }
}

resource "aws_ecr_lifecycle_policy" "expire_images_statistics_server" {
  repository = aws_ecr_repository.statistics_server.name

  policy = templatefile("./templates/ecr/expire-policy.json", {})
}
