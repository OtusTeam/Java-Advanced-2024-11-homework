plugins {
    id("java")
    id("org.springframework.boot") version "3.3.4"
    id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok")
    implementation("com.h2database:h2")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    manifest {
        attributes("Start-Class" to "com.otus.java.advanced.Main")
    }
}

graalvmNative {
    testSupport = true
    metadataRepository {
        enabled.set(true)
    }
}