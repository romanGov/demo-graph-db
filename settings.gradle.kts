rootProject.name = "demo-graph-db"

pluginManagement {
    plugins {
        kotlin("jvm") version  "1.8.20"
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
include("demo-rabbit")