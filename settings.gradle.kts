rootProject.name = "demo-graph-db"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.21"
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