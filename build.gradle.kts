plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.qameta.allure:allure-junit5:2.25.0")
}
tasks.test {
        useJUnitPlatform()
}
allure {
    version = "2.25.0"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}