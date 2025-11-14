plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

allure {
    version.set("2.24.0")
    adapter {                      // Настраиваем интеграцию с JUnit5
        allureJavaVersion.set("2.24.0")
        frameworks {
            junit5 {
                adapterVersion.set("2.24.0")
            }
        }
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
// 📊 Allure
    testImplementation("io.qameta.allure:allure-junit5:2.24.0")
    testImplementation("io.qameta.allure:allure-java-commons:2.24.0")
}

tasks.test {
    useJUnitPlatform()
}