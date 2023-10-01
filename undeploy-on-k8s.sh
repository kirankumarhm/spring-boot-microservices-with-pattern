#!/bin/bash

echo "Delete Discovery Service from Kubernetes"
kubectl delete -f discovery-service-statefulset.yaml
kubectl delete -f discovery-service-tcp-service.yaml
kubectl delete -f discovery-service-headless-service.yaml
sleep 2
echo "Delete Axon Server from Kubernetes"
kubectl delete -f axon-server-deployment.yaml
kubectl delete -f axon-server-service.yaml
sleep 2
echo "Delete Product Service from Kubernetes"
kubectl delete -f product-service-deployment.yaml
kubectl delete -f product-service-service.yaml
sleep 2
echo "Delete Order Service from Kubernetes"
kubectl delete -f order-service-deployment.yaml
kubectl delete -f order-service-service.yaml
sleep 2
echo "Delete Payment Service from Kubernetes"
kubectl delete -f payment-service-deployment.yaml
kubectl delete -f payment-service-service.yaml
sleep 2
echo "Delete User Service from Kubernetes"
kubectl delete -f user-service-deployment.yaml
kubectl delete -f user-service-service.yaml
sleep 2
echo "Delete API Gateway Service from Kubernetes"
kubectl delete -f api-gateway-service-deployment.yaml
kubectl delete -f api-gateway-service-tcp-service.yaml
