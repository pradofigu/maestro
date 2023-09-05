#!/bin/bash

# Aplicar Secrets e Configmaps
kubectl apply -f config/maestro-namespace.yaml
kubectl apply -f config/maestro-secrets.yaml
kubectl apply -f config/maestro-configmap.yaml

# Aplicar Services
kubectl apply -f services/svc-postgres.yaml
kubectl apply -f services/svc-maestro-app.yaml

# Aplicar Pods
kubectl apply -f database/postgres-pod.yaml
kubectl apply -f maestro-app/maestro-app.yaml
