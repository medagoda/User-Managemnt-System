# User Management Backend System (Spring Boot)

A simple user management system built with **Java Spring Boot**. This system allows for basic CRUD operations (Create, Read, Update, Delete) on user accounts.

## 📁 Project Structure

- `controller/` - REST API endpoints
- `service/` - Business logic layer
- `repository/` - Data access layer using Spring Data JPA
- `dto/` - Data Transfer Objects
- `entity/` - User entity class

## 🚀 Features

- Register new users
- Retrieve user details
- Update user information
- Delete users
- Validation and exception handling

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 / MySQL (switchable)
- Maven
- Lombok

## ⚖️ API Endpoints

| Method | Endpoint       | Description         |
|--------|----------------|---------------------|
| POST   | /api/users     | Create a new user   |
| GET    | /api/users     | Get all users       |
| GET    | /api/users/{id}| Get user by ID      |
| PUT    | /api/users/{id}| Update user by ID   |
| DELETE | /api/users/{id}| Delete user by ID   |

## ⚙️ How to Run

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/user-management-backend.git
cd user-management-backend
```

### 2. Configure Application
By default, it uses **H2 in-memory database**. You can switch to **MySQL** in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3. Build and Run
```bash
./mvnw spring-boot:run
```

### 4. Test the API
Use **Postman** or **cURL**:
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John", "email": "john@example.com"}'
```

## ✅ Example User JSON
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}
```

## 🚫 Future Improvements
- Role-based authentication with Spring Security
- JWT token integration
- Email verification
