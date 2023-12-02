import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.johnrengelman.shadow")
    application
}

group = "cz.glubo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(KotlinX.coroutines.core)
    implementation("info.picocli:picocli:_")
    kapt("info.picocli:picocli-codegen:_")
    testImplementation(kotlin("test"))
    testImplementation(KotlinX.coroutines.test)
    testImplementation(Testing.junit.jupiter.params)
    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation(Testing.kotest.framework.datatest)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}