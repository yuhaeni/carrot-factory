# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kafka와 Redis를 이용한 주문 처리 파이프라인 (Order processing pipeline using Kafka and Redis). Spring Boot 4 + Kotlin multi-module project.

## Build & Run Commands

```bash
# Build all modules
./gradlew build

# Run the API server
./gradlew :api:bootRun

# Run tests for all modules
./gradlew test

# Run tests for a specific module
./gradlew :api:test
./gradlew :core:test

# Run a single test class
./gradlew :api:test --tests "com.haeni.carrot.api.order.service.OrderServiceTest"

# Apply code formatting (Spotless - runs automatically via hook on file edits)
./gradlew spotlessApply

# Check formatting without applying
./gradlew spotlessCheck

# Start infrastructure (PostgreSQL, Kafka, Redis)
docker-compose up -d
```

## Module Architecture

```
carrot-factory/
├── core/          # Domain models, JPA entities, repositories, shared DTOs
├── infrastructure/  # Kafka producer config, Redis config
├── pipeline/      # Kafka consumer event processing
├── api/           # REST controllers, services — depends on core + infrastructure + pipeline
└── batch/         # Standalone batch processing (minimal dependencies)
```

**Dependency graph:** `api` → `pipeline` → `infrastructure` → `core`

Only `api` produces an executable JAR (`bootJar`). All other modules set `jar = true, bootJar = false`.

## Key Architecture Decisions

**DTO layering in `api`:** Controller receives `XxxRequest`, converts to `XxxRequestDto` (via extension fun `toDto()`), passes to Service. Service returns `XxxResponseDto`, which is converted to `XxxResponse` for the controller. This isolates layer concerns.

**Response wrapper:** All REST endpoints return `ApiResponse<T>` (defined in `core`) with `success`, `data`, `message`, and `errorCode` fields.

**JPA auditing:** `BaseEntity` in `core` provides `createdAt`/`updatedAt` via `@EntityListeners(AuditingEntityListener::class)`. `@EnableJpaAuditing` lives in `ApiApplication`.

**Kafka topics** (defined in `KafkaConfig`):
- `order-events` — 3 partitions, replication factor 1
- `order-events.DLT` — dead letter topic, 1 partition

**Package root:** `com.haeni.carrot` — `@SpringBootApplication(scanBasePackages = ["com.haeni.carrot"])` in `ApiApplication` scans all modules.

## Infrastructure (docker-compose)

| Service | Port | Purpose |
|---|---|---|
| PostgreSQL 15 | 5433 | Primary DB (`carrot_pipeline`) |
| Redis 7 | 6378 | Cache |
| Kafka (KRaft) | 29092 | Message broker |
| Kafka UI | 8090 | Kafka monitoring |
| Redis Commander | 8091 | Redis monitoring |

Credentials: PostgreSQL `root/root1234@`, Redis no auth.

## Hooks

- **SessionStart**: Runs `./gradlew dependencies` to verify project is ready.
- **PostToolUse (file edits)**: Auto-runs `./gradlew spotlessApply -q` on `.kt`, `.kts`, `.java`, `.json`, `.yaml`, `.yml`, `.md`, `.properties`, `.xml`, `.sql` files.