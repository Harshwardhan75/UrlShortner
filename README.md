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
