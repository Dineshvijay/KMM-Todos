import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.0"
    id("com.android.library")
    id("kotlin-parcelize")
}

kotlin {

    val iosX64 = iosX64("ios")
    val iosArm64 = iosArm64("iosArm64")

    configure(listOf(iosX64, iosArm64)) {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm:0.11.0")
        }
        binaries.framework {
            baseName = "Shared"
        }
    }
    android()

    sourceSets {
        val ktor_version = "1.5.4"
        val commonMain by getting {
            dependencies {
                implementation("dev.icerock.moko:mvvm:0.10.0")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktor_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktor_version")
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktor_version")
            }
        }
        val iosTest by getting {}
    }

    targets {
        targetFromPreset(presets.getByName("android"), "android")
    }
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(26)
        targetSdkVersion(30)
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
        getByName("test") {
            java.srcDirs("src/androidTest/kotlin")
            res.srcDirs("src/androidTest/res")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

val debugFatFramework by tasks.creating(FatFrameworkTask::class) {
    baseName = "Shared"
    val mode = "DEBUG"
    from(
        kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode),
        kotlin.targets.getByName<KotlinNativeTarget>("iosArm64").binaries.getFramework(mode)
    )
    destinationDir = buildDir.resolve("fat-framework/debug")
    group = "Universal framework"
    description = "Builds a debug universal (fat) framework"
}

val releaseFatFramework by tasks.creating(FatFrameworkTask::class) {
    baseName = "Shared"
    val mode = "RELEASE"
    from(
        kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode),
        kotlin.targets.getByName<KotlinNativeTarget>("iosArm64").binaries.getFramework(mode)
    )
    destinationDir = buildDir.resolve("fat-framework/release")
    group = "Universal framework"
    description = "Builds a release universal (fat) framework"
}

tasks.getByName("build").dependsOn(releaseFatFramework)
tasks.getByName("build").dependsOn(debugFatFramework)