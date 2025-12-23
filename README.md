# 🔗 UrlShortner – High Throughput, Scalable URL Shortening Backend

A **production-grade URL Shortener backend** built using **Java & Spring Boot**, designed to handle **~4,000 requests per second**.  
The system is optimized for **performance, scalability, reliability, and concurrency**, inspired by real-world systems like **Bitly / TinyURL**.

This project demonstrates **advanced backend engineering and system design concepts**, not just CRUD APIs.

---

## 🚀 Complete Functionality Overview

### 🔹 URL Shortening
- Converts long URLs into short, unique URLs
- Supports multiple URLs per user
- Write-light, read-heavy optimized architecture

---

### 🔹 URL Redirection (Low Latency)
- Redirects short URLs to original long URLs
- Optimized hot path for extremely fast redirects
- Designed for high-frequency access patterns

---

### 🔹 Deterministic Short URL Generation
- Long URL is **first persisted in the database**
- Uses:
  - **Primary Key (URL_ID)**
  - **Foreign Key (USER_ID)**
- Both are combined to form a unique identifier
- Identifier is encoded using **Base64 encoding**

#### Example:
URL_ID = 1
USER_ID = 1

Identifier = "1 1"
Short Code = Base64("1 1")

This guarantees **uniqueness**, avoids collisions, and enables **fast primary-key-based lookups**.

---

## ⚡ Caching Architecture (2-Level)

### 🟢 L1 Cache – In-Memory (LRU Based)
- Implemented inside JVM memory
- Uses **LRU (Least Recently Used) eviction policy**
- Stores hot / frequently accessed URLs
- Automatically evicts least recently used entries
- Provides ultra-fast access with zero network overhead

**Why LRU?**
- URL shorteners are read-heavy systems
- Recently accessed URLs are more likely to be accessed again
- LRU maximizes cache hit ratio and memory efficiency

---

### 🔵 L2 Cache – Redis (Distributed)
- Acts as a fallback cache on L1 miss
- Shared across multiple service instances
- Supports horizontal scaling
- Reduces database load

---

### 🔁 Cache Lookup Flow
Request
↓
L1 Cache (In-Memory, LRU)
↓ (miss)
L2 Cache (Redis)
↓ (miss)
Database
↓
Update Redis → Update L1 (LRU)

---

## 🔹 User-Configurable Rate Limiting

### Sliding Window Rate Limiting
- Rate limiting applied **per user**
- User can configure:
  - Maximum allowed requests
  - Time window duration
- Implemented using **Sliding Window algorithm**
- Prevents abuse and ensures fair usage

Example:
> A user can allow 100 requests per 60 seconds

---

### 🔹 Thread-Safe Rate Limiting
- **Locking applied at individual user level**
- No global locks → better scalability
- Prevents race conditions under high concurrency
- Ensures correctness even at **~4K req/sec**

---

## 🔹 Asynchronous Logging (Kafka-Based)
- URL access logs are **not written synchronously**
- Logs are published to **Kafka topics**
- Keeps redirect path extremely fast
- Avoids blocking I/O operations on critical path

---

## 🔹 Batch Database Updates
- Kafka consumers **periodically batch log records**
- Batch updates are written to database
- Significantly reduces database write load
- Improves throughput and overall system efficiency

---

## 🔹 Fault Tolerance & Data Reliability
- Kafka ensures:
  - Log data is preserved even if application crashes
  - Safe replay and recovery of events
- Prevents data loss during system failures
- Enables resilient, event-driven architecture

---

## 🔹 High Throughput Design
- Achieved **~4,000 requests/second**
- Stateless backend architecture
- Optimized read path
- Async logging + caching

---

## 🔹 Dockerized Deployment
- Application fully containerized using Docker
- **Docker Compose** orchestrates:
  - Spring Boot backend
  - Redis cache
  - Kafka broker
- Enables production-like local environment

---

## 🔹 Health Check Endpoint
- Lightweight health endpoint
- Useful for monitoring and container orchestration

---

## 🧠 System Architecture Overview
Client Request
↓
Rate Limiter (Per User, Sliding Window)
↓
L1 Cache (In-Memory, LRU)
↓
L2 Cache (Redis)
↓
Redirect Response
↓
Async Log → Kafka → Batch Consumer → Database

---

## 🧪 Performance Characteristics
- **Throughput:** ~4,000 requests/sec
- **Traffic Pattern:** Read-heavy, write-light
- **Latency:** Minimal (async logging)
- **Concurrency:** Thread-safe per-user locking
- **Reliability:** Kafka-backed event persistence
- **Scalability:** Stateless containers + Redis

---

## 🛠️ Tech Stack

| Layer | Technology |
|-----|-----------|
| Language | Java |
| Framework | Spring Boot |
| Build Tool | Maven |
| Cache (L1) | In-Memory LRU Cache |
| Cache (L2) | Redis |
| Rate Limiting | Sliding Window Algorithm |
| Concurrency | Per-User Locking |
| Messaging | Kafka |
| Logging | Async + Batch Processing |
| Containerization | Docker |
| Orchestration | Docker Compose |

---

## 📦 Project Structure
