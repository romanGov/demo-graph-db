rootProject.name = "demo-graph-db"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val springframeworkBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
    }
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://repo1.maven.org/maven2") }
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }
}
buildscript {
    val kotlinVersion: String by settings
    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}
include("demo-rabbit")