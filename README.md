# FiapSubII CarSales 
## Sobre
API REST para gerenciamento de venda de carros usados, desenvolvida em Java 17 + Spring Boot + Maven, seguindo os princípios de Clean Architecture.

A API permite:

- Cadastro e edição de veículos
- Listagem de veículos disponíveis e vendidos (ordenados por preço)
- Registro de vendas
- Processamento de pagamento via webhook
- Consulta do status do pagamento

## Arquitetura
Clean Architecture
```
src/main/java
├── domain          # Entidades e regras de negócio
├── application     # Casos de uso, serviços e DTOs
├── infrastructure  # Persistência, configurações e integrações
└── presentation    # Controllers REST e handlers de erro
```

## Tecnologias
- Java 17
- Spring Boot 3
- Maven (3.9.10)
- SQLite (in-memory)
- Hibernate / JPA
- Swagger / OpenAPI
- Docker
- Kubernetes

Sendo pré requisitos: Java 17, Maven, Docker e Kubernetes

# Debug
### Usando sempre Git Bash

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Swagger / OpenAPI

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

> Banco: SQLite em memória

## Endpoints

### Cars
- `GET /api/cars/available`
- `GET /api/cars/sold`
- `POST /api/cars`
- `PUT /api/cars/{id}`

### Sales
- `POST /api/sales`

### Webhook (pagamentos)
- `POST /api/webhooks/payments`

### Payment
- `GET /api/payment/{paymentCode}`

## Run with Docker
```
mvn clean package -DskipTests
docker build -t subii-carsales:local .
docker run --rm -p 8080:8080 subii-carsales:local
```
OR 
```
docker compose up
```

## Run with Kubernetes

- Aplicar manifests
```bash
kubectl apply -f k8s/
```
- Verifica pods 
```bash
kubectl get pods
```

- Acessar a aplicação (Expose local - estará disponível em http://localhost:8080/swagger-ui/index.html)
```bash
kubectl port-forward svc/carsales-api 8080:80
``` 

### Após atualizar aplicação - Update image and run with Kubernetes
```bash
docker build -t subii-carsales:local .
kubectl set image deployment/carsales-api carsales-api=subii-carsales:local
kubectl rollout status deployment/carsales-api
``` 

## Fluxo de funcionamento

1. Cadastro de veículo (POST /api/cars)
2. Listagem de veículos disponíveis (GET /api/cars/available)
3. Registro de venda (POST /api/sales) - Com carId listado em /api/cars/available
4. Processamento do pagamento via webhook (POST /api/webhooks/payments) - com paymentCode gerado em /api/sales
5. Atualização do status do veículo (AVAILABLE → SOLD)
6. Listagem de veículos vendidos (GET /api/cars/sold)
