plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    alias(libs.plugins.com.gradleup.shadow)
    alias(libs.plugins.org.jmailen.kotlinter)
    alias(libs.plugins.com.adarshr.test.logger)
    application
}

group = "cz.glubo"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("cz.glubo.adventofcode.MainKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.reactive)
    implementation(libs.z3.turnkey)
    implementation(libs.picocli)
    implementation(libs.slf4j.klogging)
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "20g"
    systemProperty("kotest.framework.config.fqn", "cz.glubo.adventofcode.config.ProjectConfig")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks.build {
    dependsOn("formatKotlin")
}

kotlinter {
    ignoreLintFailures = true
    ignoreFormatFailures = true
}
repositories {
    mavenCentral()
}
