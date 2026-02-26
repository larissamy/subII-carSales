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
