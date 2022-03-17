### how to start with minikube

    minikube start
    minikube -p minikube docker-env
    # To point your shell to minikube's docker-daemon, run:
    # eval $(minikube -p minikube docker-env)
    # To unset run:
    # eval $(minikube -p minikube docker-env -u)

### load images from local docker registry

    minikube image load bookworm_backend:latest
    minikube image load bookworm_postgres:latest
    minikube image load bookworm_config:latest

### secrets should be base64 encoded

    echo -n "bookworm_user" | base64

### create services

    kubectl apply -f bookworm-1-base.yaml
    kubectl apply -f bookworm-2-postgres.yaml
    kubectl apply -f bookworm-3-keycloak.yaml
    kubectl apply -f bookworm-4-backend.yaml

### delete services - execute in reversed order!

    kubectl delete -f bookworm-4-backend.yaml
    kubectl delete -f bookworm-3-keycloak.yaml
    kubectl delete -f bookworm-2-postgres.yaml
    kubectl delete -f bookworm-1-base.yaml

### get pods, services information

    kubectl get pods -n bookworm
    kubectl get services -n bookworm

### delete services by name

    kubectl delete service bookworm-load-balancer

### registry

    minikube addons configure registry-creds
    minikube addons enable registry
    kubectl port-forward --namespace kube-system service/registry 5000:80

### get ip

    minikube ip

### how to copy data to pod

    kubectl cp ./config/keycloak-db-init.sql bookworm/bookworm-postgres-789cb769d4-w8rnd:/
    kubectl cp ./config/realm-bookworm.json bookworm/bookworm-postgres-656bbf486c-cnkvl:/

### swagger address - won't work in prod

    http://minikube-ip:nodePort/swagger-ui.html
