# FiapSubII CarSales 

## Debug

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Swagger / OpenAPI

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`


> Banco: SQLite em memória (`jdbc:sqlite:file:memdb1?mode=memory&cache=shared`) com `ddl-auto=create-drop`.

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

## Run Kubernetes
### Usando sempre Git Bash
### Build Docker - Raíz do projeto
```bash
mvn clean package -DskipTests  
docker build -t subii-carsales:local .
```
- Aplicar manifests
```bash
kubectl apply -f k8s/
```
- Forçar deployment 
```bash
kubectl set image deployment/carsales-api carsales-api=subii-carsales:local
kubectl patch deployment carsales-api -p '{"spec":{"template":{"spec":{"containers":[{"name":"carsales-api","imagePullPolicy":"IfNotPresent"}]}}}}'
``` 

- Reínicio e check - Aguardar running
```bash
kubectl rollout restart deployment/carsales-api
kubectl get pods
``` 

- Expose local - estará disponível em http://localhost:8080/swagger-ui/index.html
```bash
kubectl port-forward svc/carsales-api 8080:80
``` 


