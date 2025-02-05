plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    implementation("org.modelmapper:modelmapper:3.2.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":service"))
    implementation(project(":provider"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}