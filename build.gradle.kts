plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.16.0"
}

group = "love.huhu"
version = "0.1.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

mirai {
    jvmTarget = JavaVersion.VERSION_1_8
}

val exposedVersion = "0.44.0"
val sqliteVersion = "3.44.0.0"
dependencies {
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("io.github.bonigarcia:webdrivermanager:5.6.2")
    implementation("org.seleniumhq.selenium:selenium-java:4.15.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.10")
}
tasks.test {
    useJUnitPlatform()
}