import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
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
