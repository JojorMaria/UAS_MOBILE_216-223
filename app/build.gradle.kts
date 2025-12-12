plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}
android {
    namespace = "com.example.eventapp"
    compileSdk = 36   // update ke 36

    defaultConfig {
        applicationId = "com.example.eventapp"
        minSdk = 24
        targetSdk = 36   // update ke 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true      // kamu pakai RecyclerView XML → wajib hidup
        compose = true          // biar bisa pakai UI Compose juga
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // versi yang cocok untuk Kotlin 2.0.21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // BOM Compose
    implementation(platform(libs.androidx.compose.bom))

    // Compose library
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // RecyclerView & ViewBinding
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
