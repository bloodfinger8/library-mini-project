import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

dependencies {
    implementation(project(":library-domain"))

    implementation("com.github.pengrad:java-telegram-bot-api:6.7.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("co.elastic.clients:elasticsearch-java:8.8.0")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
}
