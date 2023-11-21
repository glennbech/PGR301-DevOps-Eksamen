resource "aws_cloudwatch_dashboard" "main" {
  dashboard_name = var.prefix
  dashboard_body = <<DASHBOARD
{
  "widgets": [
    {
      "type": "metric",
      "x": 0,
      "y": 0,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          [
            "${var.prefix}",
            "count.value"
          ]
        ],
        "period": 300,
        "stat": "Maximum",
        "region": "eu-west-1",
        "title": "Total number of faces"
      }
    },
  "type": "metric",
        "x": 0,
        "y": 0,
        "width": 12,
        "height": 6,
        "properties": {
          "metrics": [
            [
              "${var.prefix}",
              "count.value"
            ]
          ],
          "period": 300,
          "stat": "Maximum",
          "region": "eu-west-1",
          "title": "Total number of faces"
        }
      },
  ]
}
DASHBOARD
}
module "alarm" {
  source = "../alarm_module"
  alarm_email = var.alarm_email
  prefix = var.prefix
}