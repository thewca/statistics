plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

// Multiple usages
val springBootVersion = "3.4.4"
val lombokVersion = "1.18.38"
val simpleFlatMapperVersion = "8.2.3"

// Single usage
val openApiVersion = "2.8.6"
val hypersistenceUtilsVersion = "3.9.0"
val mySqlConnectorVersion = "8.0.33"
val guavaVersion = "33.4.8-jre"
val restAssuredVersion = "5.5.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${springBootVersion}")

    implementation("com.google.guava:guava:$guavaVersion")
    implementation("mysql:mysql-connector-java:${mySqlConnectorVersion}")
    implementation("org.simpleflatmapper:sfm-tuples:${simpleFlatMapperVersion}")
    implementation("org.simpleflatmapper:sfm-springjdbc:${simpleFlatMapperVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:${hypersistenceUtilsVersion}")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("io.rest-assured:rest-assured:${restAssuredVersion}")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    bootJar {
        archiveFileName.set("app.jar")
    }
}
