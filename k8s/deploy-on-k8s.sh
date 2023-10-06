#!/bin/bash

echo "Deploy Discovery Service on Kubernetes"
kubectl apply -f discovery-service-statefulset.yaml
kubectl apply -f discovery-service-tcp-service.yaml
kubectl apply -f discovery-service-headless-service.yaml
sleep 2
echo "Deploy Axon Server on Kubernetes"
kubectl apply -f axon-server-deployment.yaml
kubectl apply -f axon-server-service.yaml
sleep 2
echo "Deploy Product Service on Kubernetes"
kubectl apply -f product-service-deployment.yaml
kubectl apply -f product-service-service.yaml
sleep 2
echo "Deploy Order Service on Kubernetes"
kubectl apply -f order-service-deployment.yaml
kubectl apply -f order-service-service.yaml
sleep 2
echo "Deploy Payment Service on Kubernetes"
kubectl apply -f payment-service-deployment.yaml
kubectl apply -f payment-service-service.yaml
sleep 2
echo "Deploy User Service on Kubernetes"
kubectl apply -f user-service-deployment.yaml
kubectl apply -f user-service-service.yaml
sleep 2
echo "Deploy API Gateway Service on Kubernetes"
kubectl apply -f api-gateway-service-deployment.yaml
kubectl apply -f api-gateway-service-tcp-service.yaml
