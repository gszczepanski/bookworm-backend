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
}

resource "azurerm_resource_group" "it-sec" {
  name     = "Security"
  location = "westeurope"

  lifecycle {
    prevent_destroy = true
  }
}

resource "azurerm_container_registry" "secregistry" {
  name                = "secregistry"
  resource_group_name = azurerm_resource_group.it-sec.name
  location            = azurerm_resource_group.it-sec.location
  sku                 = "Standard"
  admin_enabled       = true
}
