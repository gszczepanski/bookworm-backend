### terraform

## enter stage directory and issue commands

    terraform init
    terraform validate

    terraform plan -out create.tfplan
    terraform apply create.tfplan -auto-approve

    terraform plan -destroy -out destroy.tfplan
    terraform apply destroy.tfplan -auto-approve
