# file contains example how to store secrets - do not commit to repo for non-demo project
variable "spring_profile" {
  default = "prod"
}


variable "postgres" {
  type = map
  default = {
    "db"  = "bookworm_library"
    "user" = "bookworm_user"
    "password" = "xyzXYZxyz"
  }
}


variable "keycloak" {
  type = map
  default = {
    "user" = "admin"
    "password" = "admin"
    "import" = "/tmp/realm-bookworm.json"
    "db_vendor"  = "postgres"
    "db_addr"  = "bookworm-postgres"
    "db_database_name"  = "bookworm_keycloak"
    "db_user"  = "bookworm_keycloak_user"
    "db_password"  = "bookworm_keycloak_p"
    "extra_args" = "-Dkeycloak.profile.feature.upload_scripts=enabled"
  }
}

variable "azure_registry" {
  type = map
  default = {
    "user" = "SecRegistry"
    "password" = "TODO-PUT-PASSWORD_HERE"
  }
}
