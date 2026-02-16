# 🦾 Ultron Automation Framework

## 📌 Overview

Ultron is a scalable, enterprise-ready test automation framework designed for modern SDET workflows.

Built using:

- Java 17  
- Selenium WebDriver  
- TestNG  
- REST Assured  
- Maven  
- Jenkins (CI Integration)  

Ultron is designed to support:

- UI Automation  
- API Automation  
- Database Validation  
- Data-Driven Testing  
- Parallel Execution  
- CI/CD Pipelines  

---

## 🏗 Architecture

Ultron follows a modular layered architecture:

### 🔹 Core Framework Layer
- Driver Management
- Configuration Management
- Utilities
- Reporting
- Logging

### 🔹 UI Automation Layer
- Page Object Model (POM)
- JSON-based Locator Strategy
- Explicit Wait Handling
- Screenshot Capture

### 🔹 API Automation Layer
- REST Assured integration
- Request/Response validation
- Schema validation
- Token-based authentication support

### 🔹 Database Layer
- JDBC-based validation utilities
- Query execution and result verification

### 🔹 Test Layer
- TestNG-based execution
- Data-driven capability
- Retry mechanism
- Listener integration
- Parallel execution support

---

## 🧠 Design Patterns Used

- Page Object Model (POM)
- Factory Pattern (Driver Creation)
- Singleton Pattern (Configuration Management)
- ThreadLocal (Parallel Execution Support)
- Listener Pattern (Reporting & Retry Handling)

---

## 🚀 Key Features

- Multi-browser support
- Environment-based configuration
- JSON-driven locators
- Extent Reports integration
- Log4j2 logging
- Screenshot capture on failure
- Jenkins CI integration
- Scalable modular structure
- Easily extendable to BDD (Cucumber)

---

## ⚙️ Execution

### ▶ Run Full Test Suite

```bash
mvn clean test


## 👨‍💻 Author

Pandurang Choudhari~~

