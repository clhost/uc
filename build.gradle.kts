import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    application
    kotlin("jvm") version "1.8.10"
    id("com.dorongold.task-tree") version "2.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.mikeneck.graalvm-native-image") version "v1.4.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.clhost"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.5.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

application {
    applicationName = "uc"
    mainClass.set("io.clhost.uc.UcKt")
}

nativeImage {
    graalVmHome = System.getenv("GRAALVM_HOME") ?: System.getenv("JAVA_HOME")
    executableName = "uc"
    buildType { build -> build.executable(main = "io.clhost.uc.UcKt") }
    arguments {
        add("--no-fallback")
        add("--pgo-instrument")
        add("--allow-incomplete-classpath")
        add("-H:ReflectionConfigurationFiles=reflection.json")
    }
    outputDirectory = file("$buildDir/native")
}

tasks {
    test {
        useJUnitPlatform()
    }
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
            allWarningsAsErrors = true
        }
    }
    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
            allWarningsAsErrors = true
        }
    }
    shadow {
        archivesName.set("uc")
    }
}
