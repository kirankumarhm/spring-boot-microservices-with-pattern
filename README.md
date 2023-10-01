# Spring Boot Microservices, CQRS, SAGA, Axon Framework, Docker, Kubernetes

### Microservice with Saga Pattern


1. Eureka Discovery Service
2. Product Service
3. Order Service
4. User Service
5. Payment Service
6. API Gateway Service
7. Core Library (Common Class used in above services)

#### Step-by-step process to start applications without docker-compose
Execute below command to build jar files on the parent directory (spring-boot-microservices-with-pattern)      

```
mvn clean package -DskipTests
```
Note: Since I have added the jib plugin, this will automatically create docker image and push it to the docker registry.
You can change the desired docker registry in the pom.xml

Reference :  
https://cloud.google.com/java/getting-started/jib  
https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#quickstart


. First start the Axon Server  
. Second one start the discovery-service  
. Third, fourth and fifth you can start any other service except api-gateway-service.  
. Lastly, start api-gateway-service

#### Step-by-step process to start applications with docker-compose
```
docker compose up
```

once all the applications are started successfully,
To insert product  
```
POST http://localhost:8084/api/product
{
    "title": "iPad 1",
    "price" : 1200,
    "quantity": 1
}
```
To retrieve product
```
GET http://localhost:8084/api/product
```

To place an order
```
POST http://localhost:8084/api/order
{
    "productId": "b0ffa99a-1629-43e5-9915-e8b845e0a811",
    "quantity": 1,
    "addressId": "030fac23-559f-4110-aa8a-5ccdaa4a2505"
}
```

## Kubernetes 

Install kompose in your system and convert the docker-compose.yml   
For installation refer https://kompose.io/installation/  

```
kompose -f docker-compose.yml convert --volumes hostPath
```
Note: I have provided the options while converting as --volumes hostPath. This is just because I am using minikube and want to refer local /tmp path
In production we need to create PersistentVolumeClaims and attach to deployments

* It really does not matter whether you have created profiles in pom.xml and with respect to each environment different properties for building docker image.
* Each property entries can be overridden by Kubernetes Deployment environment variables.
### Special case for discovery-service [Eureka Server/Registry Service] :- 
We need to create headless service and refer that headless service in StatefulSet

* **StatefulSet** is required to maintain the host registry, as pod will be created with the dynamic has value appended with pod name. and we need to refer with hostname. StatefulSet does not work alone, we have to create headless service.
* **Headless service** : A service is a without ip address and creates endpoint

So, I am commenting out the profiles segment and keep it simple build in the discovery-service pom.xml  
And as I mentioned in the #2 I will delete application-dev.properties and application-prod.properties

Once every thing is ready, execute deployment shell script to deploy on to minikube

```
./deploy-on-k8s.sh
```

