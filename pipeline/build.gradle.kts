plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar { enabled = false }
tasks.jar {
    enabled = true
    archiveClassifier.set("")  // ← 추가
}

dependencies {
    implementation(project(":core"))
    implementation(project(":infrastructure"))

    // Spring boot
    implementation("org.springframework.boot:spring-boot-starter")

    // Kafka Consumer
    implementation("org.springframework.kafka:spring-kafka")

    // JPA
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}



