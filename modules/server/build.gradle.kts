
plugins {
    id("org.springframework.boot") version "2.4.8"
    id("com.palantir.docker") version "0.26.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    api("org.springframework.boot:spring-boot-starter-test")
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
