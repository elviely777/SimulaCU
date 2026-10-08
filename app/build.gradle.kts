plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.simulacu.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.simulacu.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE,NOTICE}"
        }
    }
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "androidx.lifecycle") {
                useVersion("2.8.4")
            }
            if (requested.group == "androidx.activity") {
                useVersion("1.9.2")
            }
            if (requested.group == "androidx.compose.material") {
                useVersion("1.7.5")
            }
            if (requested.group.startsWith("androidx.compose")) {
                if (requested.group == "androidx.compose.material3") {
                    useVersion("1.3.1")
                } else if (requested.group != "androidx.compose.material") {
                    useVersion("1.10.4")
                }
            }
        }
    }
}

dependencies {
    // Compose + Material 3
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Material Icons extended set
    implementation("androidx.compose.material:material-icons-extended-android:1.7.5")
    implementation("androidx.compose.material:material-icons-core-android:1.7.5")

    // collectAsStateWithLifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // ProcessLifecycleOwner (app foreground/background)
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // ViewModel + StateFlow for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Google Fonts (downloadable fonts)
    implementation("com.google.android.gms:play-services-base:18.5.0")
    implementation("com.google.android.gms:play-services-tasks:18.2.0")

    // JSON serialization (Gson)
    implementation("com.google.code.gson:gson:2.10.1")

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    testImplementation("androidx.room:room-testing:2.6.1")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("org.robolectric:robolectric:4.14.1")
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
