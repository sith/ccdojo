plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "afedorov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.hamcrest:hamcrest:2.2")
    implementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}