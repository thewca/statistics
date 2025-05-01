resource "aws_default_vpc" "default" {
  provider = aws.no_tags

  lifecycle {
    ignore_changes = [tags, tags_all]
  }
}
