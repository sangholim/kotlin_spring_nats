import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
}

group = "com.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://oss.sonatype.org/content/repositories/") }
}

extra["springCloudVersion"] = "2021.0.0-RC1"

dependencies {
	//spring
	implementation("org.springframework.boot:spring-boot-autoconfigure:2.6.1")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-autoconfigure-processor")
	implementation("org.springframework:spring-messaging")
	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	//implementation("org.springframework.cloud:spring-cloud-stream")
	// nats

	implementation("io.nats:nats-spring-cloud-stream-binder:0.5.0")
	implementation("io.nats:nats-spring:0.5.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
