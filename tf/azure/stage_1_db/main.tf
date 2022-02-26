terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 2.65"
    }
  }

  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}

  skip_provider_registration = true
}

locals {
  service_name   = "bookworm"
  owner          = "Bookworm Team"
  readiness_time = 120
}

 resource "azurerm_resource_group" "it-sec" {
   name     = "Bookworm"
   location = "westeurope"

   lifecycle {
     prevent_destroy = true
   }
 }

#resource "azurerm_automation_account" "it-sec" {
#  name                = "it-sec-auto-acc"
#  location            = azurerm_resource_group.it-sec.location
#  resource_group_name = azurerm_resource_group.it-sec.name
#
#  sku_name = "Basic"
#
#  tags = {
#    environment = "test"
#  }
#}

resource "azurerm_storage_account" "it-sec-storage" {
  name                     = "${local.service_name}storage"
  resource_group_name      = azurerm_resource_group.it-sec.name
  location                 = azurerm_resource_group.it-sec.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
  account_kind             = "Storage"
  min_tls_version          = "TLS1_2"
}

resource "azurerm_storage_share" "it-sec-share" {
  name                 = "${local.service_name}-share"
  storage_account_name = azurerm_storage_account.it-sec-storage.name
  quota                = 50
}

resource "azurerm_storage_share_file" "postgres-init-file" {
  name             = "keycloak-db-init.sql"
  storage_share_id = azurerm_storage_share.it-sec-share.id
  source           = "../../init/keycloak-db-init.sql"
}

resource "azurerm_storage_share_file" "keycloak-init-file" {
  name             = "realm-bookworm.json"
  storage_share_id = azurerm_storage_share.it-sec-share.id
  source           = "../../init/realm-bookworm.json"
}

resource "azurerm_container_group" "it-sec-container-group" {
  name                = "${local.service_name}-instance"
  resource_group_name = azurerm_resource_group.it-sec.name
  location            = azurerm_resource_group.it-sec.location
  ip_address_type     = "public"
  dns_name_label      = local.service_name
  os_type             = "linux"

  exposed_port {
    port     = 8000
    protocol = "TCP"
  }

  image_registry_credential {
    server   = "secregistry.azurecr.io"
    username = var.azure_registry.user
    password = var.azure_registry.password
  }

  container {
    name   = "${local.service_name}-postgres"
    image  = "secregistry.azurecr.io/${local.service_name}_postgres:latest"
    cpu    = "1"
    memory = "1.5"
    secure_environment_variables = {
      POSTGRES_DB : var.postgres.db
      POSTGRES_USER : var.postgres.user
      POSTGRES_PASSWORD : var.postgres.password
    }

    ports {
      port     = 5432
      protocol = "TCP"
    }

    readiness_probe {
      http_get {
        port = "5432"
      }
      initial_delay_seconds = local.readiness_time
      period_seconds        = local.readiness_time
    }

    volume {
      name       = "${local.service_name}-share"
      mount_path = "/docker-entrypoint-initdb.d"
      read_only  = false
      share_name = azurerm_storage_share.it-sec-share.name

      storage_account_name = azurerm_storage_account.it-sec-storage.name
      storage_account_key  = azurerm_storage_account.it-sec-storage.primary_access_key
    }
  }

   container {
     name   = "${local.service_name}-keycloak"
     image  = "jboss/keycloak:15.1.0"
     cpu    = "1"
     memory = "1.5"
     secure_environment_variables = {
       KEYCLOAK_USER : var.keycloak.user
       KEYCLOAK_PASSWORD : var.keycloak.password
       KEYCLOAK_IMPORT : var.keycloak.import
       KEYCLOAK_DB_VENDOR : var.keycloak.db_vendor
       KEYCLOAK_DB_ADDR : var.keycloak.db_addr
       KEYCLOAK_DB_DATABASE : var.keycloak.db_database_name
       KEYCLOAK_DB_USER : var.keycloak.db_user
       KEYCLOAK_DB_PASSWORD : var.keycloak.db_password
       KEYCLOAK_EXTRA_ARGS : var.keycloak.extra_args
     }
  
     readiness_probe {
      http_get {
        port = "8080"
      }
       initial_delay_seconds = local.readiness_time
       period_seconds = local.readiness_time
     }
  
     ports {
       port     = 8080
       protocol = "TCP"
     }
  
   }

   container {
     name   = "${local.service_name}-backend"
     image  = "secregistry.azurecr.io/${local.service_name}_backend:latest"
     cpu    = "1"
     memory = "1.5"
     environment_variables = {
       SPRING_PROFILES_ACTIVE : var.spring_profile
     }
  
     readiness_probe {
      http_get {
        port = "8000"
      }
       initial_delay_seconds = local.readiness_time
       period_seconds = local.readiness_time
     }
  
     ports {
       port     = 8000
       protocol = "TCP"
     }
   }

  tags = {
    environment = "test"
  }
}
