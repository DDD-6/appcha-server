
plugins {
    id("com.palantir.docker") version "0.26.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    api("org.springframework.boot:spring-boot-starter-test")
}

docker {
    name = "appcha-server"
}
