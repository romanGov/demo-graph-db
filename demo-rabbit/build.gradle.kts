import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.util.*

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.flywaydb.flyway") version "9.8.1"
}
java.sourceCompatibility = JavaVersion.VERSION_11
group = "com.otus"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo1.maven.org/maven2/") }
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
    implementation("org.flywaydb:flyway-core:9.16.0")
    //database
    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    //spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // for spring-boot app
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


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
val appProperties = File(rootDir, "/demo-rabbit/src/main/resources/application.properties").inputStream().use {
    Properties().apply { load(it) }
}
flyway {
    url = appProperties["spring.datasource.url"] as String
    user = appProperties["spring.datasource.username"] as String
    password = appProperties["spring.datasource.password"] as String
    baselineOnMigrate = true
    schemas = arrayOf("demo_graph")
    createSchemas = true
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    outOfOrder = true
}
