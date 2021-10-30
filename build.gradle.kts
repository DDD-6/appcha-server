import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

val kotlinVersion: String by project
val springBootVersion: String by project
val protobufVersion: String by project
val grpcVersion: String by project

plugins {
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

allprojects {
	group = "com.editor.appcha"
	version = "0.0.1-SNAPSHOT"
}
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.kapt")
	apply(plugin = "java")
	apply(plugin = "idea")
	apply(plugin = "io.spring.dependency-management")

	repositories {
		mavenCentral()
	}

	configure<DependencyManagementExtension> {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
			mavenBom("com.google.protobuf:protobuf-bom:$protobufVersion")
			mavenBom("io.grpc:grpc-bom:$grpcVersion")
		}
		dependencies {
			dependency("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
			dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
		}
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		implementation("software.amazon.awssdk:dynamodb:2.17.37")
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_16
		targetCompatibility = JavaVersion.VERSION_16
	}

	tasks.compileKotlin {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "16"
		}
	}

	tasks.compileTestKotlin {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "16"
		}
	}
}
