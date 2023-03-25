import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.flywaydb.flyway") version "9.3.0"
}

group = "com.otus"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    val arcadeDbVersion: String by project
    val testContainersVersion: String by project
    val postgresDriverVersion: String by project
    implementation("org.flywaydb:flyway-core:9.8.1")
    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation(kotlin("test"))
}

val postgresUrl: String by project
val postgresUser: String by project
val postgresPassword: String by project

flyway {
    url = postgresUrl as String
    user = postgresUser as String
    password = postgresPassword as String
    baselineOnMigrate = true
    createSchemas = true
    locations = arrayOf("filesystem:src/main/resources/db")
    outOfOrder = false
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}