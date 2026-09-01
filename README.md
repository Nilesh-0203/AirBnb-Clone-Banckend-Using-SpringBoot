# 🏨 Airbnb-Style Hotel Booking Application

A backend **Hotel Booking Application** inspired by platforms like Airbnb, built using **Spring Boot, Spring Security, JWT, PostgreSQL, JPA, Stripe, and REST APIs**.

This project goes beyond basic CRUD operations. It implements a complete hotel booking workflow including **user authentication, hotel management, room management, availability/inventory, dynamic pricing, booking management, guest handling, payments, refunds, and role-based authorization**.

The project was built to understand how a real-world backend application can be designed using **Spring Boot and modern backend development practices**.

---

# 🚀 Features

## 👤 User Management

* 📝 User registration
* 🔐 User login
* ♻️ JWT token refresh
* 👤 User profile
* ✏️ Update user profile
* 📋 View user's bookings
* 🔒 Secure authenticated APIs

---

## 🔐 Authentication & Authorization

The application uses **Spring Security + JWT** for authentication and authorization.

### Authentication

* User Signup
* User Login
* JWT Access Token
* JWT Refresh Token
* JWT Authentication Filter
* Stateless authentication

### Authorization

Different APIs require different levels of access.

```text
                    👤 User
                      │
                      ▼
                🔐 JWT Token
                      │
                      ▼
              Spring Security
                      │
          ┌───────────┴───────────┐
          │                       │
          ▼                       ▼
     Authenticated           HOTEL_MANAGER
          │                       │
          ▼                       ▼
   User APIs                 Admin APIs
```

Hotel management APIs are protected using the `HOTEL_MANAGER` role.

---

# 🏨 Hotel Management

Hotel managers can manage hotels through secured APIs.

### Hotel Operations

* ➕ Create hotel
* 🔍 Get hotel details
* 📋 Get all hotels
* ✏️ Update hotel
* 🗑️ Delete hotel
* ✅ Activate hotel
* 📊 View hotel bookings
* 📈 Generate hotel reports

---

# 🛏️ Room Management

Each hotel can contain multiple rooms.

The application provides APIs to:

* ➕ Add rooms
* 📋 Get all rooms for a hotel
* 🔍 Get room details
* ✏️ Update room
* 🗑️ Delete room

---

# 🔎 Hotel Search

Users can search hotels based on:

* 📍 City
* 📅 Check-in date
* 📅 Check-out date
* 👥 Guest requirements
* 🛏️ Available rooms

The application checks inventory and availability before returning suitable hotels.

---

# 📦 Inventory Management

One of the important parts of the application is the **room inventory system**.

Inventory is maintained for rooms across dates.

The system supports:

* 📅 Date-based inventory
* 🛏️ Room availability
* 🔒 Inventory locking during booking
* 🚫 Closing room availability
* 📈 Surge factor
* 💰 Dynamic room pricing
* 🔄 Inventory updates

When a hotel is created, inventory can be initialized for its rooms for future dates.

---

# 💰 Dynamic Pricing

The application implements a **dynamic pricing system**.

Instead of having one fixed price for every day, room prices can change depending on different conditions.

The project contains multiple pricing strategies:

```text
                    💰 Pricing Service
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
     Occupancy         Holiday          Urgency
      Pricing           Pricing          Pricing
          │                │                │
          └────────────────┼────────────────┘
                           ▼
                     Surge Pricing
                           │
                           ▼
                    Final Room Price
```

Pricing strategies implemented include:

* 📊 Occupancy-based pricing
* 🎉 Holiday pricing
* ⏰ Urgency pricing
* 📈 Surge pricing
* 💰 Base pricing

This helped me understand how the **Strategy Design Pattern** can be used to implement flexible business rules.

---

# 📅 Booking System

The application implements a complete hotel booking workflow.

### Booking Flow

```text
👤 User
   ↓
🔎 Search Hotel
   ↓
🏨 Select Hotel
   ↓
🛏️ Select Room
   ↓
📅 Select Dates
   ↓
📦 Check Inventory
   ↓
🔒 Lock Inventory
   ↓
📝 Initialize Booking
   ↓
👥 Add Guests
   ↓
💰 Create Payment
   ↓
💳 Stripe Payment
   ↓
✅ Payment Confirmation
   ↓
🎉 Booking Confirmed
```

The system also supports:

* 📝 Booking initialization
* 👥 Adding guests
* 💳 Payment processing
* ❌ Booking cancellation
* 🔍 Booking status
* 💰 Refund processing

---

# 💳 Stripe Payment Integration

The application integrates **Stripe** for payment processing.

Payment flow:

```text
👤 User
   ↓
🏨 Hotel Booking
   ↓
💰 Calculate Booking Amount
   ↓
💳 Stripe Checkout
   ↓
🔔 Stripe Webhook
   ↓
✅ Verify Payment
   ↓
📦 Update Booking
   ↓
🎉 Booking Confirmed
```

The application also contains refund handling for cancelled bookings.

---

# 🔔 Stripe Webhook

A webhook endpoint is implemented to receive payment events from Stripe.

```text
Stripe
   │
   │ Payment Event
   ▼
Webhook API
   │
   ▼
Spring Boot
   │
   ▼
Booking Service
   │
   ▼
Update Booking Status
```

This introduces an important real-world backend concept:

> **The payment result does not always have to come directly from the user's request. External systems can notify our backend asynchronously using webhooks.**

---

# ⏰ Scheduled Pricing Updates

The application contains a scheduled job that updates inventory prices periodically.

```text
        ⏰ Scheduler
             │
             ▼
     Find Inventory
             │
             ▼
   Calculate Dynamic Price
             │
             ▼
    Update Inventory Price
             │
             ▼
    Update Hotel Min Price
```

The pricing update task is configured to run **every hour**.

This helped me understand how scheduled background processing can be implemented in Spring Boot.

---

# 🌐 REST API

One of the major parts of this project is the REST API layer.

The application currently contains:

## 🔢 **29 REST API Endpoints**

These APIs are organized across **8 controllers**.

---

## 🔐 Authentication APIs

### `POST /api/v1/auth/signup`

Register a new user.

### `POST /api/v1/auth/login`

Authenticate a user and receive authentication tokens.

### `POST /api/v1/auth/refresh`

Refresh the authentication token.

**Total: 3 APIs**

---

## 🏨 Hotel Browsing APIs

### `GET /api/v1/hotels/search`

Search hotels based on availability and search criteria.

### `GET /api/v1/hotels/{hotelId}/info`

Get information about a particular hotel.

**Total: 2 APIs**

---

## 🏢 Hotel Admin APIs

These APIs are protected and intended for hotel managers.

### `POST /api/v1/admin/hotels`

Create a hotel.

### `GET /api/v1/admin/hotels/{hotelId}`

Get hotel details.

### `PUT /api/v1/admin/hotels/{hotelId}`

Update hotel information.

### `DELETE /api/v1/admin/hotels/{hotelId}`

Delete a hotel.

### `PATCH /api/v1/admin/hotels/{hotelId}/activate`

Activate a hotel.

### `GET /api/v1/admin/hotels`

Get hotels.

### `GET /api/v1/admin/hotels/{hotelId}/bookings`

Get bookings for a hotel.

### `GET /api/v1/admin/hotels/{hotelId}/reports`

Get hotel reports.

**Total: 8 APIs**

---

## 🛏️ Room Management APIs

### `POST /api/v1/admin/hotels/{hotelId}/rooms`

Create a room.

### `GET /api/v1/admin/hotels/{hotelId}/rooms`

Get all rooms for a hotel.

### `GET /api/v1/admin/hotels/{hotelId}/rooms/{roomId}`

Get room details.

### `DELETE /api/v1/admin/hotels/{hotelId}/rooms`

Delete a room.

### `PUT /api/v1/admin/hotels/{hotelId}/rooms/{roomId}`

Update a room.

**Total: 5 APIs**

---

## 📦 Inventory APIs

### `GET /api/v1/admin/inventory/rooms/{roomId}`

Get inventory for a room.

### `PATCH /api/v1/admin/inventory/rooms/{roomId}`

Update room inventory.

**Total: 2 APIs**

---

## 📅 Booking APIs

### `POST /api/v1/bookings/init`

Initialize a booking.

### `POST /api/v1/bookings/{bookingId}/addGuests`

Add guests to a booking.

### `POST /api/v1/bookings/{bookingId}/payments`

Create/process payment for a booking.

### `POST /api/v1/bookings/{bookingId}/cancel`

Cancel a booking.

### `GET /api/v1/bookings/{bookingId}/status`

Get booking status.

**Total: 5 APIs**

---

## 👤 User APIs

### `PATCH /api/v1/users/profile`

Update user profile.

### `GET /api/v1/users/myBookings`

Get the user's bookings.

### `GET /api/v1/users/profile`

Get the user's profile.

**Total: 3 APIs**

---

## 🔔 Webhook API

### `POST /api/v1/webhook/payment`

Receive payment events from Stripe.

**Total: 1 API**

---

# 📊 API Summary

| Module              |        APIs |
| ------------------- | ----------: |
| 🔐 Authentication   |           3 |
| 🔎 Hotel Browsing   |           2 |
| 🏨 Hotel Admin      |           8 |
| 🛏️ Room Management |           5 |
| 📦 Inventory        |           2 |
| 📅 Booking          |           5 |
| 👤 User             |           3 |
| 💳 Payment Webhook  |           1 |
| **🚀 Total**        | **29 APIs** |

So this project is not just a CRUD application.

It contains **29 REST endpoints covering multiple real-world business operations**.

---

# 🧪 API Testing with Postman

I used **Postman** to test the REST APIs and understand how the different components of the backend communicate.

The API testing flow includes:

```text
🔐 Signup
   ↓
🔑 Login
   ↓
🎫 Receive JWT
   ↓
🔎 Search Hotels
   ↓
🏨 Select Hotel
   ↓
🛏️ Select Room
   ↓
📅 Check Availability
   ↓
📝 Initialize Booking
   ↓
👥 Add Guests
   ↓
💳 Payment
   ↓
🔔 Webhook
   ↓
✅ Booking Status
```

Postman helped me test:

* Request methods
* Request parameters
* Request bodies
* Headers
* JWT Authorization
* Role-based access
* HTTP status codes
* Validation
* Error handling
* Booking workflow
* Payment workflow

---

# 🏗️ Application Architecture

The project follows a layered Spring Boot architecture.

```text
                 👤 Client / Postman
                         │
                         ▼
                ┌─────────────────┐
                │   Controllers   │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │    Services     │
                │ Business Logic  │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │  Repositories   │
                │   Spring Data   │
                │      JPA        │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │   PostgreSQL    │
                └─────────────────┘
```

Additional components:

```text
Spring Security
      │
      ▼
JWT Authentication
      │
      ▼
Authorization
```

```text
Booking Service
      │
      ├── Inventory
      │
      ├── Pricing
      │
      └── Stripe
```

---

# 🧩 Major Components

The application contains:

### 🎮 Controllers

**8 Controllers**

Responsible for exposing REST APIs.

### ⚙️ Services

**13 Service classes**

Responsible for business logic.

### 🗄️ Repositories

**7 Repository classes**

Responsible for database operations using Spring Data JPA.

### 📦 Entities

**12 Entity classes**

Represent the application's database/domain model.

### 📨 DTOs

**17 DTO classes**

Used for transferring data between API and application layers.

### 💰 Pricing Strategies

**7 strategy-related classes**

Used to implement dynamic pricing logic.

---

# 🛠️ Tech Stack

| Technology           | Purpose                        |
| -------------------- | ------------------------------ |
| ☕ Java 21            | Programming language           |
| 🌱 Spring Boot       | Backend framework              |
| 🌐 Spring Web MVC    | REST APIs                      |
| 🗄️ PostgreSQL       | Relational database            |
| 🔗 Spring Data JPA   | Database persistence           |
| 🔐 Spring Security   | Authentication & authorization |
| 🎫 JWT               | Stateless authentication       |
| 💳 Stripe            | Payment processing             |
| 🔔 Stripe Webhook    | Payment event handling         |
| 🧩 ModelMapper       | DTO mapping                    |
| 📚 OpenAPI / Swagger | API documentation              |
| 📦 Maven             | Build & dependency management  |
| 🔀 Git & GitHub      | Version control                |
| 🧪 Postman           | API testing                    |

---

# 🗄️ Database

The application uses **PostgreSQL** as the relational database.

The domain model contains entities such as:

```text
User
 │
 ├── Booking
 │      │
 │      └── Guest
 │
Hotel
 │
 ├── Room
 │      │
 │      └── Inventory
 │
 └── Hotel Contact Information
```

The database is used to persist:

* Users
* Hotels
* Rooms
* Bookings
* Guests
* Inventory
* Hotel pricing information
* Contact information
* Booking/payment status

---

# 🔄 Complete Booking Flow

A major learning from this project was understanding that a booking is not simply:

```text
POST Booking
```

There are multiple business operations involved.

```text
                👤 User
                  │
                  ▼
             🔐 Authenticate
                  │
                  ▼
             🔎 Search Hotel
                  │
                  ▼
             🏨 Select Hotel
                  │
                  ▼
              🛏️ Select Room
                  │
                  ▼
             📅 Select Dates
                  │
                  ▼
          📦 Check Inventory
                  │
                  ▼
            💰 Calculate Price
                  │
                  ▼
           📝 Initialize Booking
                  │
                  ▼
             👥 Add Guests
                  │
                  ▼
             💳 Make Payment
                  │
                  ▼
            🔔 Stripe Webhook
                  │
                  ▼
          ✅ Confirm Booking
```

This gave me practical exposure to designing **multi-step business workflows**.

---

# 🧠 Design Patterns & Concepts

This project helped me explore several important backend concepts:

### 🔹 Layered Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### 🔹 Strategy Pattern

Used for dynamic pricing.

```text
PricingStrategy
      │
      ├── Base Pricing
      ├── Occupancy Pricing
      ├── Holiday Pricing
      ├── Urgency Pricing
      └── Surge Pricing
```

### 🔹 DTO Pattern

DTOs are used to control the data exposed through APIs.

### 🔹 Repository Pattern

Spring Data JPA repositories handle database operations.

### 🔹 JWT Authentication

Stateless authentication using JWT tokens.

### 🔹 Role-Based Access Control

Different APIs are accessible depending on the user's role.

### 🔹 Webhooks

External payment systems can notify the backend asynchronously.

### 🔹 Scheduled Jobs

Background tasks automatically update pricing and inventory-related data.

---

# 🧪 Error Handling

The application also contains centralized exception handling.

This helps provide consistent API responses for situations such as:

* Resource not found
* Unauthorized access
* Invalid requests
* Access denied
* Business rule violations
* Payment-related errors

The application uses custom response/error structures instead of exposing raw exceptions directly.

---

# 📂 Project Structure

```text
airBnbApp/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nk/airBnbApp/
│   │   │
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── dto/
│   │   │       ├── security/
│   │   │       ├── strategy/
│   │   │       ├── advice/
│   │   │       ├── config/
│   │   │       ├── exception/
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

---

# ⚙️ Local Setup

## Prerequisites

Make sure you have:

* Java 21+
* Maven
* PostgreSQL
* Git
* Postman

---

## 1. Clone the Repository

```bash
git clone <your-github-repository-url>

cd airBnbApp
```

---

## 2. Configure PostgreSQL

Create the database:

```sql
CREATE DATABASE airBnb;
```

Configure the database connection in:

```text
src/main/resources/application.properties
```

---

## 3. Run the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

The application uses:

```text
/api/v1
```

as its context path.

---

# 🧪 Testing with Postman

After starting the application, use Postman to test the APIs.

Example:

```text
http://localhost:8080/api/v1/auth/signup
```

For protected APIs, first login and obtain the JWT token.

Then send:

```text
Authorization: Bearer <JWT_TOKEN>
```

with the request.

---

# 📚 API Documentation

The project includes **OpenAPI/Swagger integration**, making it easier to explore and test the REST APIs.

Swagger can be used to understand:

* Available APIs
* Request methods
* Request parameters
* Request bodies
* API responses
* Authentication requirements

---

# 🧠 What I Learned

This project was a major step beyond building simple CRUD applications.

I learned how different backend components work together to create a real-world application.

### Backend Development

* Building REST APIs with Spring Boot
* Layered architecture
* Spring Data JPA
* PostgreSQL integration
* DTO-based API design
* Exception handling

### Security

* Spring Security
* JWT authentication
* JWT filters
* Password encryption
* Stateless authentication
* Role-based authorization

### Business Logic

* Hotel management
* Room management
* Booking workflow
* Inventory management
* Availability checking
* Dynamic pricing
* Booking cancellation
* Refund handling

### Payment Integration

* Stripe integration
* Checkout/payment flow
* Payment webhooks
* Payment status handling
* Refund processing

### Advanced Backend Concepts

* Strategy Design Pattern
* Scheduled jobs
* Database locking during inventory updates
* External service integration
* API testing using Postman

---

# 📊 Project by the Numbers

```text
🚀 29 REST APIs
🎮 8 Controllers
⚙️ 13 Service Classes
🗄️ 7 Repository Classes
📦 12 Entity Classes
📨 17 DTO Classes
💰 7 Pricing/Strategy Classes
🔐 JWT Authentication
👥 Role-Based Authorization
💳 Stripe Payment Integration
🔔 Stripe Webhook
⏰ Scheduled Pricing Updates
🗄️ PostgreSQL Database
🧪 Postman API Testing
📚 OpenAPI / Swagger
```

---

# 🔮 Future Improvements

Possible improvements include:

* 🐳 Dockerize the application
* 🧩 Docker Compose for PostgreSQL
* ☁️ Deploy to AWS
* ⚙️ CI/CD with GitHub Actions
* 🔄 CI/CD with Jenkins
* 📊 Monitoring with Prometheus & Grafana
* 📝 Better automated test coverage
* 🧪 Integration testing
* 🔐 Secret management
* 🔒 HTTPS
* 📦 Redis caching
* 📨 Kafka-based event-driven architecture
* ☸️ Kubernetes deployment
* 🌐 API Gateway
* 📈 Advanced booking analytics

---

# 🎯 Project Goal

The goal of this project was to understand how a real-world backend system is built beyond simple CRUD operations.

A simple CRUD application might look like:

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Database
```

But a real booking system looks more like:

```text
                    👤 User
                       │
                       ▼
                  🔐 JWT Auth
                       │
                       ▼
                 🔎 Hotel Search
                       │
                       ▼
                  🏨 Hotel
                       │
                       ▼
                   🛏️ Room
                       │
                       ▼
                📦 Inventory
                       │
                       ▼
                💰 Dynamic Pricing
                       │
                       ▼
                 📅 Booking
                       │
                       ▼
                  👥 Guests
                       │
                       ▼
                 💳 Stripe
                       │
                       ▼
                🔔 Webhook
                       │
                       ▼
                ✅ Confirmation
```

This project helped me move from **learning individual Spring Boot concepts to understanding how multiple backend concepts work together in a real-world application.**

---

# 🚀 Final Takeaway

> **A booking button looks simple from the outside. The backend behind it is not. 😄**

This project helped me understand that a production-style backend involves much more than creating APIs.

It involves:

**Authentication → Authorization → Business Logic → Database → Inventory → Pricing → Booking → Payments → Webhooks → Error Handling**

And this project gave me hands-on experience with each of these areas.

---

## 👨‍💻 Author

**Nilesh Kudale**

Java | Spring Boot | Spring Security | PostgreSQL | JWT | REST APIs | Backend Development

---

## ⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐.

**One more project completed. The backend journey continues. 🚀**
