⚙️ Bare Metal HTTP Server
----

A lightweight HTTP server and micro-framework built from scratch in Java, designed to demonstrate a practical understanding of networking, HTTP, concurrency, reflection, routing, and backend architecture—without relying on high-level web frameworks.



📌 About the Project
----

Bare Metal HTTP Server is an educational backend project that implements the fundamental building blocks of a web server directly with Java's standard networking APIs.

Instead of depending on frameworks such as Spring Boot, the project manually handles the HTTP request lifecycle, including socket communication, request parsing, routing, controller discovery, reflection-based method invocation, response generation, and concurrent client processing.

The main objective is to demonstrate how backend frameworks work internally by recreating a simplified version of their core concepts.

Why this project matters

This project highlights knowledge beyond simply consuming frameworks:

- Understanding of the HTTP protocol

- Low-level communication using TCP sockets

- Manual request parsing

- Custom routing infrastructure

- Annotation-driven endpoint mapping

- Java Reflection API

- Concurrent request handling with thread pools

- Separation between framework and application layers

- REST-style API development


🏗️ Architecture
----

The application is organized into two main layers:

Bare-metal-server

│

├── application

│   ├── controller

│   │   └── UserController.java

│   ├── db

│   │   └── Database.java

│   └── model

│       └── User.java

│

├── framework

│   ├── annotations

│   │   ├── GET.java

│   │   ├── POST.java

│   │   ├── PUT.java

│   │   ├── DELETE.java

│   │   └── RestController.java

│   │

│   ├── http

│   │   ├── Request.java

│   │   ├── Response.java

│   │   ├── Router.java

│   │   └── HttpHandler.java

│   │

│   ├── server

│   │   ├── HttpServer.java

│   │   └── ClientHandler.java

│   │

│   └── service

│       └── RouterScanner.java

│

└── Main.java

> Architectural responsibility

> Layer

> Responsibility

> application

> Business-oriented application code, controllers, models, and data handling

> framework.annotations

> Declarative HTTP endpoint metadata

> framework.http

> HTTP request, response, routing, and handler abstractions

> framework.server

> Socket lifecycle and concurrent client processing

> framework.service

> Reflection-based controller scanning and route registration

🔄 Request Lifecycle
----

The request processing flow is implemented manually:

Client
  │
  ▼
TCP Socket Connection
  │
  ▼
ServerSocket
  │
  ▼
Thread Pool
  │
  ▼
ClientHandler
  │
  ▼
Request Parser
  │
  ▼
Router
  │
  ▼
Controller Method
  │
  ▼
Response Builder
  │
  ▼
HTTP Response

Step-by-step

1. The server opens a ServerSocket on the configured port.

2. Incoming client connections are accepted.

3. Each client is processed through an ExecutorService thread pool.

4. The raw HTTP request is parsed into a custom Request object.

5. The router identifies the matching HTTP method and path.

6. The corresponding controller method is invoked.

7. Java Reflection dynamically executes the endpoint method.

8. A custom Response object generates the HTTP response.

9. The response is returned to the client.

**✨ Core Features**

🌐 Custom HTTP Server
----

The project uses Java's native networking APIs:

- ServerSocket

- Socket

- InputStream

- OutputStream

This allows direct control over the communication between client and server.

**🧭 Custom Router**

Routes are registered internally using the combination:

HTTP_METHOD + PATH

Example:

- GET:/api/users

- POST:/api/users

- DELETE:/api/users

The router then dispatches incoming requests to their corresponding handlers.

**🏷️ Annotation-Based Routing**

Endpoints are declared through custom annotations:

@GET("/api/users")
public String listUsers() {
    // ...
}

Supported HTTP method annotations:

- Annotation

- HTTP Method

- @GET

- GET

- @POST

- POST

- @PUT

- PUT

- @DELETE

- DELETE

This approach resembles the declarative programming model used by modern Java web frameworks.

**🔍 Reflection-Based Controller Scanner**

The RouterScanner inspects controller methods at runtime and automatically registers routes.

Conceptually:

Controller
    ↓

Reflection Scan
    ↓

Find HTTP Annotation
    ↓

Extract Path
    ↓

Register Route

This demonstrates practical use of the Java Reflection API for framework-level development.


⚡ Concurrent Request Processing
----

The server uses a fixed thread pool to process multiple client connections concurrently:

ExecutorService threadPool = Executors.newFixedThreadPool(50);

Each accepted socket connection is delegated to a ClientHandler, allowing the server to avoid blocking the main accept loop while a request is being processed.

**📦 Custom Request Parsing**

The Request class is responsible for parsing:

> HTTP method

> Request path

> HTTP version

> Query parameters

> Headers

> Request body

**Example request:**

GET /api/users/detail?id=1 HTTP/1.1

Host: localhost:8080

The framework extracts the query parameter and makes it available to the controller.

**📤 Custom Response Generation**

Responses are manually assembled following the HTTP protocol structure:

HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: ...

Supported status responses include:

- 200 OK

- 201 Created

- 202 Accepted

- 400 Bad Request

- 401 Unauthorized

- 403 Forbidden

- 404 Not Found

- 500 Internal Server Error


🚀 Getting Started
----

Prerequisites

Before running the project, make sure you have:

- Java JDK 17+

- Gradle (optional when using the Gradle Wrapper)

- Clone the repository

- git clone <YOUR_REPOSITORY_URL>
cd Bare-metal-server

- Run with Gradle

- On Linux/macOS:

- ./gradlew build

On Windows:

- gradlew.bat build

Then run the application from your IDE or execute the compiled main class according to your environment.


🖥️ Server Configuration
----

The server currently starts on:

Port: 8080

Host: localhost

The entry point is:

> public static void main(String[] args) {
   
>  HttpServer server = new HttpServer();
    
>   server.registerController(new UserController());
    
>  server.start(8080);

> }

After startup, the API can be accessed through:

http://localhost:8080


🔌 API Endpoints
----

> Get all users

> GET /api/users

Example:

- curl http://localhost:8080/api/users

- Get user by ID

- GET /api/users/detail?id=1

Example:

- curl "http://localhost:8080/api/users/detail?id=1"

- Create a user

- POST /api/users

Example:

curl -X POST http://localhost:8080/api/users \
  
  -H "Content-Type: application/json" \
  
  -d '{"name":"John Doe","email":"john@example.com"}'

Delete a user

DELETE /api/users?id=1

Example:

curl -X DELETE "http://localhost:8080/api/users?id=1"

**🧪 Example Response**

> {
  
>  "id": 1,
 
>  "name": "John Doe",
 
>  "email": "john@example.com"

> }


🛠️ Technologies and Concepts
----

1. Technology / Concept

2. Purpose

3. Java

4. Core programming language

5. Gradle

6. Build automation and dependency management

7. SLF4J

8. Application logging

9. Java Sockets

10. TCP client/server communication

11. HTTP/1.1

12. Application-layer communication protocol

13. Java Reflection

14. Runtime controller inspection and invocation

15. Concurrency

16. Multi-client processing through thread pools

17. Custom Annotations

18. Declarative route definition


🎯 Engineering Skills Demonstrated
----

This project was designed to showcase practical backend engineering knowledge, including:

Object-Oriented Programming

Backend Architecture

HTTP Fundamentals

TCP/IP Communication

Socket Programming

Java Reflection

Custom Annotations

Thread Management

Concurrent Programming

Routing Design

REST API Concepts

Exception Handling

Logging

Build Automation with Gradle


📈 Potential Improvements
----

The project is intentionally lightweight and provides a strong foundation for future evolution. Potential next steps include:

Full JSON serialization/deserialization

Request validation layer

Middleware / filter pipeline

Dynamic route parameters (/users/{id})

Dependency Injection container

Database integration

Connection lifecycle improvements

Configurable thread pool

HTTP keep-alive support

Unit and integration tests

Improved HTTP status handling

Centralized exception handling

Content negotiation

Request/response interceptors

API documentation


💡 What Makes This Project Different?
----

Most backend projects focus exclusively on using established frameworks.

This project takes a different approach.

Instead of only writing:

@RestController
@RequestMapping("/users")

the underlying mechanisms that make this programming model possible are explored and partially implemented from scratch.

The project therefore serves as a practical demonstration of the concepts behind:

Web servers

Routing engines

Annotation-driven frameworks

Reflection-based discovery

Request dispatching

HTTP response construction

It represents an important exercise in understanding what happens underneath modern backend abstractions.


👨‍💻 Author
----

Vitório Santos

Backend Developer | Java Developer

LinkedIn: www.linkedin.com/in/vitorio-santos


📄 License
----

This project is intended for educational and portfolio purposes.

<div align="center">


⭐ If you found this project interesting, consider giving it a star!
----

Built to understand the foundations behind modern Java backend frameworks.

</div>
