output "login_server" {
  value = azurerm_container_registry.secregistry.login_server
}

output "admin_enabled" {
  value = azurerm_container_registry.secregistry.admin_enabled
}

output "admin_username" {
  value = azurerm_container_registry.secregistry.admin_username
  sensitive = true
}

output "admin_password" {
  value = azurerm_container_registry.secregistry.admin_password
  sensitive = true
}
