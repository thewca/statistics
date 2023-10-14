resource "aws_cloudwatch_event_rule" "statistics_cron" {
  name                = "statistics-cron-${terraform.workspace}"
  description         = "Statistics cron"
  schedule_expression = "cron(0 5 ? * TUE,FRI *)" # Tuesday, Friday. Kind of before competitions and after results posted
}

resource "aws_cloudwatch_event_target" "statistics_cron" {
  rule      = aws_cloudwatch_event_rule.statistics_cron.name
  target_id = "SubmitStatisticsJob"
  arn       = aws_batch_job_queue.statistics_cron_job_queue.arn
  role_arn  = aws_iam_role.event_bus_invoke_remote_event_bus.arn
  batch_target {
    job_definition = aws_batch_job_definition.statistics_cron_job_definition.arn
    job_name       = "statistics-cron-${terraform.workspace}"
  }
}

resource "aws_iam_role" "event_bus_invoke_remote_event_bus" {
  name               = "event-bus-statistics-${terraform.workspace}"
  assume_role_policy = templatefile("${path.module}/templates/policies/assume-role-events.json", {})
}

data "aws_iam_policy_document" "event_bus_invoke_remote_event_bus" {
  statement {
    effect  = "Allow"
    actions = ["batch:SubmitJob"]
    resources = [
      aws_batch_job_definition.statistics_cron_job_definition.arn,
      aws_batch_job_queue.statistics_cron_job_queue.arn
    ]
  }
}

resource "aws_iam_policy" "event_bus_invoke_remote_event_bus" {
  name   = "event-bus-invoke-statistics-${terraform.workspace}"
  policy = data.aws_iam_policy_document.event_bus_invoke_remote_event_bus.json
}

resource "aws_iam_role_policy_attachment" "event_bus_invoke_remote_event_bus" {
  role       = aws_iam_role.event_bus_invoke_remote_event_bus.name
  policy_arn = aws_iam_policy.event_bus_invoke_remote_event_bus.arn
}
