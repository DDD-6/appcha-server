import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.gradle.plugins.ide.idea.model.IdeaModel

val protobufVersion: String by project
val grpcKotlinVersion: String by project
val grpcStarterVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.15")
    }
}

plugins {
    id("org.springframework.boot") version "2.4.8"
    id("com.palantir.docker") version "0.26.0"
    id("com.google.protobuf") version "0.8.15"
}

dependencies {
    implementation("com.google.protobuf:protobuf-java")
    implementation("com.google.protobuf:protobuf-java-util")
    implementation("javax.annotation:javax.annotation-api:1.2")

    api("io.grpc:grpc-protobuf")
    api("io.grpc:grpc-stub")
    api("io.grpc:grpc-netty")
    api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("net.devh:grpc-server-spring-boot-starter:$grpcStarterVersion") {
        exclude(group = "io.grpc", module = "grpc-netty-shaded")
    }
    protobuf(files("$rootDir/appcha-protobuf/protobuf"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    api("org.springframework.boot:spring-boot-starter-test")
}

configure<IdeaModel> {
    module {
        sourceDirs.add(file("$projectDir/src/main/proto"))
        testSourceDirs.add(file("$projectDir/src/test/proto"))
        generatedSourceDirs.add(file("$projectDir/build/generated/source/proto/main/java"))
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
    }

    generateProtoTasks {
        (ofSourceSet("main") + ofSourceSet("test"))
            .forEach {
                it.plugins {
                    id("grpc")
                    id("grpckt")
                }
            }
    }
}

tasks.jar.get().enabled = true
tasks.bootJar.get().enabled = false

val gradleUserHomeDir = project.gradle.gradleUserHomeDir.normalize()

docker {
    name = "appcha-server"
    files("build/libs/server-0.0.1-SNAPSHOT.jar", "../../bin/run-java.sh")

    copySpec.with(
        copySpec {
            from(configurations.runtimeClasspath.get().filter { it.normalize().startsWith(gradleUserHomeDir) }) {
                into("lib")
            }
        },
        copySpec {
            from(configurations.runtimeClasspath.get().filter { !it.normalize().startsWith(gradleUserHomeDir) }) {
                into("project-lib")
            }
        }
    )
}

tasks.dockerPrepare.get().mustRunAfter(tasks.build.get())
