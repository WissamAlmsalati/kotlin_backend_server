plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")
    implementation("io.ktor:ktor-jackson:1.6.7")
    implementation ("io.ktor:ktor-client-core:1.6.7")
    implementation ("io.ktor:ktor-client-cio:1.6.7")
    implementation ("io.ktor:ktor-client-jackson:1.6.7")


    implementation("mysql:mysql-connector-java:8.0.27")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("mysql:mysql-connector-java:8.0.27")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}