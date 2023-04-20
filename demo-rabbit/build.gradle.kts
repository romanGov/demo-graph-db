import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.5.21"
    id("nu.studer.jooq") version "6.0.1"
}
java.sourceCompatibility = JavaVersion.VERSION_11
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


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
