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
          ["${var.prefix}", "mask_violation.value"],
          ["${var.prefix}", "mask_passed.value"]
        ],
        "period": 300,
        "stat": "Maximum",
        "region": "eu-west-1",
        "title": "Number of violation and number of passed"
      }
    },
    {
      "type": "metric",
      "x": 12,
      "y": 6,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          [
            "${var.prefix}",
            "face_check_latency.avg", "exception", "none", "method", "scanForPPE", "class", "com.example.s3rekognition.controller.RekognitionController"
          ]
        ],
        "period": 300,
        "stat": "Average",
        "region": "eu-west-1",
        "title": "latency of scanForPPE"
      }
    },
 {
      "type": "metric",
      "x": 12,
      "y": 6,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          [
            "${var.prefix}",
            "check_hands.avg", "exception", "none", "method", "scanForHands", "class", "com.example.s3rekognition.controller.RekognitionController"
          ]
        ],
        "period": 300,
        "stat": "Average",
        "region": "eu-west-1",
        "title": "latency for hands "
      }
    },
    {
      "type": "metric",
      "x": 0,
      "y": 0,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          ["${var.prefix}", "hands_count.value"]
        ],
        "period": 300,
        "stat": "Maximum",
        "region": "eu-west-1",
        "title": "Number of hands"
      }
    },
    {
      "type": "metric",
      "x": 18,
      "y": 60,
      "width": 6,
      "height": 6,
      "properties": {
        "metrics": [
          ["${var.prefix}", "hands_count_gauge.value"]
        ],
        "view": "gauge",
        "title": "Number of hands found",
        "region": "eu-west-1",
        "yAxis": {
          "left": {
            "min": 0,
            "max": 100
          }
        }
      }
    }
  ]
}

DASHBOARD
}
module "alarm" {
  source = "../alarm_module"
  alarm_email = var.alarm_email
  prefix = var.prefix
}