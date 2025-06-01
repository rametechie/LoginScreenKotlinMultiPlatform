import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    val iosDeploymentTarget = "17.0"
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64("iosX64"),
        iosArm64("iosArm64"),
        iosSimulatorArm64("iosSimulatorArm64")
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    // Apply iOS deployment target to all native targets
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {

        }
    }

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
        }
        commonTest.dependencies {
//            implementation(libs.kotlin.test)
        }
    }

    tasks.register("assembleDebugAppleFrameworkForXcode") {
        dependsOn("linkDebugFrameworkIosArm64")
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
            freeCompilerArgs += "-Xapple.deployment-target=$iosDeploymentTarget"
        }
    }

    val buildType = "debugFramework"
    val frameworkName = "Shared"

    tasks.register<Exec>("assembleXCFramework") {
        group = "build"
        description = "Create XCFramework manually"

        dependsOn(
            ":shared:linkDebugFrameworkIosArm64",
            ":shared:linkDebugFrameworkIosX64",
            ":shared:linkDebugFrameworkIosSimulatorArm64"
        )


        val outputDir = layout.buildDirectory.get().asFile.resolve("xcframework")
        val arm64 = buildDir.resolve("bin/iosArm64/$buildType/$frameworkName.framework")
        val simArm64 = buildDir.resolve("bin/iosSimulatorArm64/$buildType/$frameworkName.framework")

        commandLine(
            "xcodebuild", "-create-xcframework",
            "-framework", arm64.absolutePath,
            "-framework", simArm64.absolutePath,
            "-output", outputDir.resolve("$frameworkName.xcframework").absolutePath
        )
    }
}

android {
    namespace = "com.jetbrains.simplelogin.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
