plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

group = "com.ua.money24"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_19
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2022.0.4"
extra["telegrambotsVersion"] = "6.7.0"
extra["sqliteVersion"] = "3.42.0.0"
extra["hibernateDialectsVersion"] = "6.3.0.CR1"

dependencies {
    // https://mvnrepository.com/artifact/org.telegram/telegrambots-spring-boot-starter
    implementation("org.telegram:telegrambots-spring-boot-starter:${property("telegrambotsVersion")}")
    // https://mvnrepository.com/artifact/org.telegram/telegrambotsextensions
    implementation("org.telegram:telegrambotsextensions:${property("telegrambotsVersion")}")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    runtimeOnly("org.xerial:sqlite-jdbc:${property("sqliteVersion")}")
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects
    implementation("org.hibernate.orm:hibernate-community-dialects:${property("hibernateDialectsVersion")}")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}