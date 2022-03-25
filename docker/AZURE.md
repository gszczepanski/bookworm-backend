### Deployment to Azure Container Instances with docker

Create resource group Bookworm.

    az group create -l westeurope -n Bookworm

Create Azure Container Registry in resource group Bookworm.

    az acr create --resource-group Bookworm --name bwregistry --sku Basic

Tag and push local images to newly created Azure Container Registry.

    docker tag bookworm_backend bwregistry.azurecr.io/bookworm_backend:latest
    docker push bwregistry.azurecr.io/bookworm_backend:latest

    docker tag bookworm_postgres bwregistry.azurecr.io/bookworm_postgres:latest
    docker push bwregistry.azurecr.io/bookworm_postgres:latest

Enable admin user.

    az acr update -n bwregistry --admin-enabled true

Print registry credentials.

    az acr credential show -n bwregistry

Login to Azure with local docker.

    docker login azure

Create Azure Container Instances context and make docker use that context.

    docker context create aci sec-context
    docker context use sec-context

Use `docker compose` - not `docker-compose`(!) to start services.

    docker compose -f azure-compose.yaml -p backend-instance up

Check resource group _Bookworm_ on Azure Portal for running containers. 
