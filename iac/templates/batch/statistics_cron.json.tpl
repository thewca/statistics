{
    "command": [],
    "image": "${image}",
    "environment": [
        {
            "name": "STATISTICS_PORT",
            "value": "${statistics_port}"
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
            "name": "DB_USERNAME",
            "value": "${db_username}"
        },
        {
            "name": "DB_PASSWORD",
            "value": "${db_password}"
        },
        {
            "name": "DB_PORT",
            "value": "${db_port}"
        }
    ],
    "fargatePlatformConfiguration": {
        "platformVersion": "LATEST"
    },
    "networkConfiguration": {
        "assignPublicIp": "ENABLED"
    },
    "resourceRequirements": [
        {
            "type": "VCPU",
            "value": "4"
        },
        {
            "type": "MEMORY",
            "value": "8192"
        }
    ],
    "executionRoleArn": "${execution_role}"
}