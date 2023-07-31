# Statistics IAC

This is the IAC for the statistics project, using terraform and AWS.

## Requirements

- Terraform
- AWS account

## Setup

- Install [terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli?in=terraform/aws-get-started)

- Start terraform

  `terraform init`

- Create or select new workspace

  `terraform workspace select prod || terraform workspace new prod`

- Run plan

  `terraform plan`

## Resources created manually

To avoid the usage of variables in terraform (like tfvars), some resources are created manually and referenced here. You need to create them and run the project (or run when there are changes in the variables).

```bash
export region=us-west-2
export environment=prod
export app_id="SOME_APP_ID_HERE"
export write_user="USERNAME_HERE"
export write_password="STRONG_PASSWORD_HERE"
export read_user="USERNAME_HERE"
export read_password="ANOTHER_STRONG_PASSWORD_HERE"

```

```bash
aws ssm put-parameter --name "/config/$environment/statistics/app/id"                   --value $app_id          --overwrite --region $region --type String
aws ssm put-parameter --name "/config/$environment/statistics/dumped_db/write_user"     --value $write_user      --overwrite --region $region --type String
aws ssm put-parameter --name "/config/$environment/statistics/dumped_db/write_password" --value $write_password  --overwrite --region $region --type String
aws ssm put-parameter --name "/config/$environment/statistics/dumped_db/read_user"      --value $read_user       --overwrite --region $region --type String
aws ssm put-parameter --name "/config/$environment/statistics/dumped_db/read_password"  --value $read_password   --overwrite --region $region --type String
```
