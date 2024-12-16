import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    id("org.jmailen.kotlinter")
    application
}

group = "cz.glubo"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("cz.glubo.adventofcode.MainKt")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(KotlinX.coroutines.core)
    implementation("info.picocli:picocli:_")
    implementation("io.klogging:slf4j-klogging:_")
    testImplementation(kotlin("test"))
    testImplementation(KotlinX.coroutines.test)
    testImplementation(Testing.junit.jupiter.params)
    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation(Testing.kotest.framework.datatest)
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "20g"
}

tasks.withType<KotlinJvmCompile> {
    kotlinOptions.jvmTarget = "21"
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks.build {
    dependsOn("formatKotlin")
}

kotlinter {
    ignoreFailures = true
}
repositories {
    maven("https://gitlab.com/api/v4/projects/64578383/packages/maven")
}
