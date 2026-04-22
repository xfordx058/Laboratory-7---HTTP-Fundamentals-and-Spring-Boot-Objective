# EcommerceApi - Product API

A simple REST API for managing e-commerce products. Built with Spring Boot.

---

## What This Project Does

This API lets you:
- View all products
- View one product by ID
- Create new products
- Update products (full or partial)
- Delete products
- Filter products by category, price range, or name

---

## How to Run

### What You Need
- Java 17 or higher
- Maven (or use the included Maven wrapper)

### Steps

```bash
# Go to project folder
cd EcommerceApi

# Run the app
./mvnw spring-boot:run

# On Windows, use:
# mvnw.cmd spring-boot:run
```

The app runs at: `http://localhost:8080`

---

## API Endpoints

| Method | URL | What It Does |
|--------|-----|--------------|
| GET | `/api/v1/products` | Get all products |
| GET | `/api/v1/products/{id}` | Get one product |
| GET | `/api/v1/products/filter` | Filter products |
| POST | `/api/v1/products` | Create a product |
| PUT | `/api/v1/products/{id}` | Update a product fully |
| PATCH | `/api/v1/products/{id}` | Update a product partially |
| DELETE | `/api/v1/products/{id}` | Delete a product |

---

## Status Codes

| Code | When It Happens |
|------|-----------------|
| 200 OK | Request worked (GET, PUT, PATCH) |
| 201 Created | New product made (POST) |
| 204 No Content | Product deleted (DELETE) |
| 400 Bad Request | Invalid data sent |
| 404 Not Found | Product ID does not exist |
| 500 Server Error | Something broke on server |

---

## Example Requests

### 1. Get All Products

```http
GET http://localhost:8080/api/v1/products
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Wireless Mouse",
    "description": "Ergonomic wireless mouse",
    "price": 29.99,
    "category": "Electronics",
    "stockQuantity": 150,
    "imageUrl": "https://example.com/mouse.jpg"
  }
]
```

---

### 2. Get One Product

```http
GET http://localhost:8080/api/v1/products/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Wireless Mouse",
  "price": 29.99,
  "category": "Electronics",
  "stockQuantity": 150
}
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2026-04-22T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product with ID 99 not found"
}
```

---

### 3. Create a Product

```http
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "Gaming Headset",
  "description": "Surround sound gaming headset",
  "price": 79.99,
  "category": "Electronics",
  "stockQuantity": 30,
  "imageUrl": "https://example.com/headset.jpg"
}
```

**Response (201 Created):**
```json
{
  "id": 11,
  "name": "Gaming Headset",
  "description": "Surround sound gaming headset",
  "price": 79.99,
  "category": "Electronics",
  "stockQuantity": 30,
  "imageUrl": "https://example.com/headset.jpg"
}
```

**Response (400 Bad Request - missing name):**
```json
{
  "timestamp": "2026-04-22T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "name": "Product name is required"
  }
}
```

---

### 4. Update Product Fully

```http
PUT http://localhost:8080/api/v1/products/1
Content-Type: application/json

{
  "name": "Updated Mouse",
  "description": "Better mouse",
  "price": 34.99,
  "category": "Electronics",
  "stockQuantity": 100,
  "imageUrl": "https://example.com/new.jpg"
}
```

**Response (200 OK):**
Returns the updated product.

---

### 5. Update Product Partially

```http
PATCH http://localhost:8080/api/v1/products/1
Content-Type: application/json

{
  "price": 19.99,
  "stockQuantity": 50
}
```

**Response (200 OK):**
Returns the product with only those fields changed.

---

### 6. Delete Product

```http
DELETE http://localhost:8080/api/v1/products/1
```

**Response:** No body, status 204 No Content.

---

### 7. Filter Products

**By category:**
```http
GET http://localhost:8080/api/v1/products/filter?filterType=category&filterValue=Electronics
```

**By price range (min-max):**
```http
GET http://localhost:8080/api/v1/products/filter?filterType=price&filterValue=10-50
```

**By name (partial match):**
```http
GET http://localhost:8080/api/v1/products/filter?filterType=name&filterValue=mouse
```

---

## Validation Rules

| Field | Rule |
|-------|------|
| name | Required, cannot be empty |
| price | Required, must be greater than 0 |
| category | Required, cannot be empty |
| stockQuantity | Required, must be 0 or more |
| description | Optional |
| imageUrl | Optional |

---

## Project Structure

```
EcommerceApi/
├── controller/          - Handles HTTP requests
│   └── ProductController.java
├── service/             - Business logic
│   └── ProductService.java
├── model/               - Data classes
│   └── Product.java
├── exception/           - Error handling
│   ├── GlobalExceptionHandler.java
│   └── ProductNotFoundException.java
└── resources/
    └── application.properties
```

---

## Tech Stack

- Spring Boot 3.3.0
- Spring Web
- Spring Validation
- Lombok
- Maven

---

## Known Limits

- Data is stored in memory (ArrayList). All data is lost when the app restarts.
- No real database is used.
- No login or security.

---

## Authors

- Anquilo, Glen Ford C. - Model, Service, Controller, Documentation
- Magallanes Kai Justin - Exceptions, Validation
- Section B
---

## School Lab Activity for WS101 Lab 7
