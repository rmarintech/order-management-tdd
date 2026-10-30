# 🛒 Order Management TDD

**Order Management TDD** is a Java backend portfolio project built to practice **Test-Driven Development (TDD)**, **Domain-Driven Design (DDD)** and **Hexagonal Architecture** through small, verifiable increments.

The emphasis is on **business invariants, clear application boundaries, dependency inversion and an auditable Red → Green → Refactor workflow**. The project is evolving incrementally; features marked as planned are **not yet implemented**.

## 🎯 Project Goal

Build an order-management backend whose domain rules are designed and tested before introducing databases, transport layers or additional infrastructure.

The Git history deliberately retains separate **RED** and **GREEN** commits where useful to show how tests guide implementation. A **REFACTOR** commit is made only when code changes are needed.

## 🚀 Tech Stack

| Area | Technologies / practices |
| --- | --- |
| Language | Java 21 |
| Framework & build | Spring Boot 4.1, Maven, Maven Wrapper |
| Testing | JUnit 5, Mockito, parameterized tests |
| Development approach | TDD — Red → Green → Refactor |
| Domain modeling | DDD, Aggregate Root, Value Objects, domain invariants |
| Architecture | Hexagonal Architecture, input/output ports, application services, Dependency Inversion |
| Version control | Git, GitHub, incremental commits |

> **Planned, not yet implemented:** PostgreSQL, Spring Data JPA persistence adapter, REST controllers and Testcontainers integration tests.

## 🏗️ Architecture Overview

The implementation keeps domain behavior separate from frameworks and infrastructure:

```text
              [ Inbound adapter: REST ]          (planned)
                         |
                         v
           GetOrderUseCase (input port)
                         |
                         v
          GetOrderService (application)
                         |
                         v
           OrderRepository (output port)
                         |
                         v
            [ JPA persistence adapter ]         (planned)
                         |
                         v
                  [ PostgreSQL ]                (planned)

       Domain: Order, OrderItem, OrderId, ProductId, OrderStatus
```

**Current scope:** the application use case and repository port exist; `GetOrderService` is tested with a Mockito repository mock. The persistence and REST adapters are upcoming work.

## 🏛️ DDD — Domain Model

```text
Order (Aggregate Root)
├── OrderId (Value Object)
├── OrderStatus: DRAFT / CONFIRMED / CANCELLED
└── OrderItem(s)
    ├── ProductId (Value Object)
    ├── quantity
    └── unitPrice
```

The `Order` aggregate controls modifications and lifecycle transitions. `OrderItem` enforces valid quantities and prices and calculates its own line total. The order total is derived from its line items rather than stored as duplicate state.

## ✅ Implemented Features

- **Order creation:** unique identifier, initial `DRAFT` status, empty items and zero total.
- **Order items:** add items while the order is a draft; positive quantity, non-negative price, mandatory product ID and unit price.
- **Pricing:** line total (`quantity × unitPrice`) and aggregate order total, including an empty-order total of zero.
- **Order lifecycle:** confirm non-empty draft orders; cancel draft orders; reject invalid repeated transitions.
- **Aggregate protection:** reject item additions to confirmed or cancelled orders; expose a read-only items list.
- **Application use case:** retrieve an order by ID using an output repository port; return `Optional.empty()` when not found.

## 🧪 TDD & Testing Strategy

Each new behavior is developed in small steps:

1. 🔴 **RED** — write a test that fails for the expected reason.
2. 🟢 **GREEN** — implement only what is needed to pass the test.
3. 🔵 **REFACTOR** — improve structure while keeping tests green.

The test suite currently focuses on:

| Test class | Scope |
| --- | --- |
| `OrderTest` | Order creation, item management, total price and state transitions |
| `OrderItemTest` | Line-item behavior and price calculation |
| `GetOrderServiceTest` | Application-layer retrieval with a mocked `OrderRepository` |

Unit tests run without requiring a running PostgreSQL instance or Spring application context.

## ⚙️ Getting Started

**Requirements:** JDK 21 and Git. The Maven Wrapper is included.

```bash
git clone https://github.com/rmarintech/order-management-tdd.git
cd order-management-tdd
```

Run the tests:

```powershell
# Windows PowerShell
.\mvnw.cmd clean test
```

```bash
# macOS / Linux
./mvnw clean test
```

## 🗺️ Roadmap

- ⬜ Implement the persistence adapter using Spring Data JPA and PostgreSQL.
- ⬜ Add integration tests with Testcontainers.
- ⬜ Expose application use cases through REST controllers.
- ⬜ Add HTTP/API tests and document the API.
- ⬜ Extend the order-management use cases as new requirements emerge.

## 👨‍💻 Author

**Rubén Marín** — Senior Java Backend Developer  
GitHub: [@rmarintech](https://github.com/rmarintech)

This repository is a practical portfolio project demonstrating **test-driven backend development, explicit domain modeling and maintainable architecture**.
