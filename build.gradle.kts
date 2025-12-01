group = "com.michael-bull.advent"
version = "1.0-SNAPSHOT"

plugins {
    application
    kotlin("jvm") version "2.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.1")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.github.michaelbull.advent2025.MainKt")
}

tasks.test {
    jvmArgs = listOf("-Xss2m")
}

tasks.withType(JavaExec::class.java) {
    jvmArgs = listOf("-Xss2m")
}
