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
![system flow](https://github.com/user-attachments/assets/5de3dbb3-bc15-4691-b602-c48a3e864b90)


---


## Entity-Relationship Diagram (ERD)
![erd2](https://github.com/user-attachments/assets/487a4490-cfcd-4ce8-9699-90769c39bd73)

---

## Kafka Inner Service Communication
![kafka](https://github.com/user-attachments/assets/6d4775c9-6d67-4932-9a12-de221ca7939f)


---

## API Documentation  

All endpoints start with
`/api/v1`

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





