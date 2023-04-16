import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    id("nu.studer.jooq") version "8.2"
    id("org.flywaydb.flyway") version "9.3.0"
}
java.sourceCompatibility = JavaVersion.VERSION_17
group = "com.otus"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo1.maven.org/maven2") }
}
tasks.withType<BootJar> {
    manifest {
        attributes["Main-Class"] = "com.otus.AppKt"
    }
}
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.2")
    }

}

dependencies {
    val arcadeDbVersion: String by project
    val testContainersVersion: String by project
    val postgresDriverVersion: String by project

    //database
    implementation("org.flywaydb:flyway-core:9.3.0")
    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresDriverVersion")
    //spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //kotlin
    implementation(kotlin("stdlib"))
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.fasterxml.jackson.module:jackson-module-paranamer")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")

    //test
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
