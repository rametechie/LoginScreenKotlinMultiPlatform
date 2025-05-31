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
        val androidUnitTest by getting

        // If you also have instrumented tests, you would add:
        // val androidInstrumentedTest by getting

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosTest by creating {
            dependsOn(commonTest)
        }

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
    // You might need to configure sourceSet names here too if you have custom paths
    // sourceSets {
    //     getByName("main") { java.srcDirs("src/androidMain/kotlin") }
    //     getByName("test") { java.srcDirs("src/androidUnitTest/kotlin") } // For local unit tests
    //     getByName("androidTest") { java.srcDirs("src/androidInstrumentedTest/kotlin") } // For instrumented tests
    // }
}