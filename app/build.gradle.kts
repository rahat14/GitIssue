import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Existing plugins
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
    id("de.mannodermaus.android-junit5") version "1.11.0.0"
}


val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
apikeyPropertiesFile.inputStream().use { inputStream ->
    apikeyProperties.load(inputStream)
}

val apiKey = apikeyProperties.getProperty("git_token") ?: error("git_token not found in apikey.properties")


android {

    namespace = "com.syntext.error.gitissue"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.syntext.error.gitissue"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "org.junit.runners.JUnit4" // Use JUnit 4 for instrumentation tests
        testInstrumentationRunnerArguments["runnerBuilder"] =  "de.mannodermaus.junit5.AndroidJUnit5Builder"// Use JUnit 5 for local unit tests
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "git_token", apikeyProperties["git_token"].toString())

    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
//    composeOptions {
//       // kotlinCompilerExtensionVersion = "1.5.1"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}





dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.jupiter.junit.jupiter)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    /////Ballast State Management
    implementation(libs.ballast.core)
    implementation(libs.ballast.repository)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    //image
    implementation(libs.coil.compose)
    //markdown viewer
    implementation (libs.compose.markdown)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // Koin for dependency injection
    implementation (libs.koin.android)
    implementation (libs.koin.core)
    implementation (libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation (libs.mockk)
    testImplementation (libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit.platform.launcher)

}


