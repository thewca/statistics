[
  {
    "name": "statistics-server-app",
    "image": "${app_image}",
    "cpu": ${fargate_cpu},
    "memory": ${fargate_memory},
    "networkMode": "awsvpc",
    "logConfiguration": {
      "logDriver": "awslogs",
      "options": {
        "awslogs-group": "/ecs/statistics-server-app",
        "awslogs-region": "${aws_region}",
        "awslogs-stream-prefix": "ecs"
      }
    },
    "portMappings": [
      {
        "containerPort": ${app_port},
        "hostPort": ${app_port}
      }
    ],
    "environment": [
      {
        "name": "APP_ID",
        "value": "${app_id}"
      },
      {
        "name": "DB_USERNAME",
        "value": "${db_username}"
      },
      {
        "name": "DB_PORT",
        "value": "${db_port}"
      },
      {
        "name": "SPRING_PROFILES_ACTIVE",
        "value": "${spring_profile}"
      },
      {
        "name": "DB_HOST",
        "value": "${db_host}"
      },
      {
        "name": "DB_DATABASE",
        "value": "${db_name}"
      },
      {
        "name": "DB_PASSWORD",
        "value": "${db_password}"
      }
    ]
  }
]