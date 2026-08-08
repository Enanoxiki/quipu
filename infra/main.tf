terraform {

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.99"
    }
  }

  required_version = ">= 1.9"
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "csv_processor_uploads" {
  bucket = "quipu-csv-processor-uploads"
}

resource "aws_s3_bucket_notification" "csv_processor_notification" {
  bucket = aws_s3_bucket.csv_processor_uploads.id
  queue {
    events = ["s3:ObjectCreated:*"]
    queue_arn = aws_sqs_queue.csv_processing.arn
  }
}

resource "aws_dynamodb_table" "csv_processing_jobs" {
  name         = "csv-processing-jobs"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "jobId"

  attribute {
    name = "jobId"
    type = "S"
  }
}

resource "aws_sqs_queue" "csv_processing_dlq" {
  name = "csv-processing-dlq"
}

resource "aws_sqs_queue" "csv_processing" {
  name = "csv-processing-queue"

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.csv_processing_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue_policy" "csv_processing" {
  queue_url = aws_sqs_queue.csv_processing.id
  policy    = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = { Service = "s3.amazonaws.com" }
      Action = "sqs:SendMessage"
      Resource = aws_sqs_queue.csv_processing.arn
      Condition = {
        ArnEquals = { "aws:SourceArn" = aws_s3_bucket.csv_processor_uploads.arn }
      }
    }]
  })
}
