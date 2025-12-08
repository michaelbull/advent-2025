group = "com.michael-bull.advent"
version = "1.0-SNAPSHOT"

plugins {
    application
    kotlin("jvm") version "2.2.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.4")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.github.michaelbull.advent2025.MainKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    jvmArgs = listOf("-Xss2m")
}

tasks.withType(JavaExec::class.java) {
    jvmArgs = listOf("-Xss2m")
}
