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


# Git 워크플로우 규칙

## 1. Repository
- **GitHub Repository:** `https://github.com/yuhaeni/usagi-app-api.git`
- **Main Branch:** `main`

## 2. Branching Strategy
- 모든 기능 개발은 `feature/[이슈번호]-[간단-설명-kebab-case]` 형식의 브랜치에서 진행한다.
- 이슈 번호가 없는 간단한 수정은 `fix/[간단-설명]` 또는 `chore/[간단-설명]` 브랜치를 사용한다.

## 3. Commit Message Convention
- 모든 커밋 메시지는 **Conventional Commits** 명세를 따른다.
- 커밋 메세지 내용은 한글로 작성한다.
- (예: `feat: 작성자 프로필 구성 요소 추가`, `fix: 기존 이미지 삭제 로직 추가`)
- 커밋 본문에는 변경 이유를 명확히 서술하고, 관련된 GitHub 이슈가 있는 경우에는 `Closes #[이슈번호]` 형식으로 반드시 포함한다.

## 4. Pull Request (PR) Process
- 모든 코드는 `main` 브랜치로 직접 푸시할 수 없으며, 반드시 PR을 통해 코드 리뷰를 받아야 한다.
- PR 제목은 커밋 메시지와 동일한 형식을 따른다.
- PR 본문은 `.github/PULL_REQUEST_TEMPLATE.md` 템플릿을 사용한다.