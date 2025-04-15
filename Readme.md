<h1 align="center">Payment Gateway System</h1>

---

##  Table of Contents

- [Introduction](#introduction)
  - [Overview](#overview)
  - [Features](#features)
  - [Technologies Used](#technologies-used)
- [Architecture Overview](#architecture-overview)
  - [System Design Overview](#system-design-overview)
  - [Microservices Responsibilities](#microservices-responsibilities)
- [System Flow](#system-flow)
- [Entity-Relationship Diagram (ERD)](#entity-relationship-diagram-erd)
- [Kafka Inner Service Communication](#kafka-inner-service-communication)

- [API Documentation](#api-documentation)
  - [Merchant Service](#merchant-service)
  - [Payment Service](#payment-service)


---

## Introduction

### Overview
A simplified Payment Gateway System designed to mimic core features of real-world platforms like Paymob.  
This project handles merchant management, secure payment links, and transaction validation, following microservices best practices.

---

### Features  

- Merchant registration and login with API key generation  
- Payment link creation for secure, one-time payments  
- Card data validation and balance checking simulation  
- Microservices architecture for modular scalability  
- Docker for isolated MySQL environments  
- Apache Kafka for asynchronous inter-service communication  

---

### Technologies Used  

- Java, Spring Boot  
- MySQL  
- Docker  
- Apache Kafka  
- Eureka Discovery Server  

---

## Architecture Overview  

### System Design Overview
The system is split into several dedicated microservices, each responsible for a specific domain.  
Communication between services is handled via Kafka.

---

### Microservices Responsibilities  

- **Merchant Service**  
  Handles merchant registration, login, profile management, and payment link creation.

- **Payment Service**  
  Handles payment processing using generated payment links.

- **Bank Service**  
  Validates card data, balalnce and simulates account transactions.

- **Api Gateway**  
  Directs external requests to the right service.

- **Eureka Discovery Server**  
  Service registry for locating microservices dynamically.

---

## System Flow
![Concept map - Page 1 (4)](https://github.com/user-attachments/assets/5ffe73e1-e1ef-4704-b8a8-9eedd0be4d36)


---


## Entity-Relationship Diagram (ERD)
![Concept map - Page 1](https://github.com/user-attachments/assets/966a42fc-f7fd-4f24-b100-e5c22f33459a)

---

## Kafka Inner Service Communication
![Concept map - Page 1 (3)](https://github.com/user-attachments/assets/5368c904-498f-4d28-a751-6ff612445d30)


---

## API Documentation  

###  Merchant Service  

| Method | Endpoint                    | Description                           |
|--------|-----------------------------|---------------------------------------|
| POST   | `/merchants/register`       | Register a new merchant.              |
| POST   | `/merchants/login`          | Authenticate a merchant.              |
| GET    | `/merchants/profile`        | Get merchant profile info.            |
| PUT    | `/merchants/card`           | Update merchant card details.         |
| PUT    | `/merchants/profile`        | Update profile information.           |
| POST   | `/merchants/payment-link`   | Generate a secure payment link.       |

---

### Payment Service  

| Method | Endpoint                   | Description                          |
|--------|----------------------------|--------------------------------------|
| POST   | `/payments/{linkId}/pay`   | Process payment via payment link.    |

---





