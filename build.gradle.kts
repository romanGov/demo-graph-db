group = "com.otus"
version = "1.0-SNAPSHOT"
plugins {
    kotlin("jvm")
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        mavenLocal()

        maven { url = uri("https://repo1.maven.org/maven2") }
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }
}
