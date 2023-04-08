import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	kotlin("kapt")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

dependencies {
	implementation(project(":library-config"))
	implementation(project(":library-domain"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	//암호화
	implementation("org.bouncycastle:bcprov-jdk15on:1.70")

	//QueryDSL
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	//swagger
	implementation("org.springdoc:springdoc-openapi-ui:1.6.15")

	//jjwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}