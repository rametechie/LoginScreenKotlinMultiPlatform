plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val androidMain by getting
        val androidTest by getting

        // 1. Create the common iOS source set
        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosTest by creating {
            dependsOn(commonTest)
        }

        // 2. Make platform-specific iOS source sets depend on the common iOS source sets
        //    You can get them by name after the targets are defined.
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosX64Test by getting {
            dependsOn(iosTest)
        }
        val iosArm64Test by getting {
            dependsOn(iosTest)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    compileSdk = 34
    namespace = "com.jetbrains.simplelogin.shared"
    defaultConfig {
        minSdk = 24
    }
}