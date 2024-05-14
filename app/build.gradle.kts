plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.aptoide_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aptoide_app"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {
    implementation(rootProject.libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    implementation(libs.coil)
    implementation(libs.hilt.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.moshiConverter)
    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.okHttp)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(rootProject.libs.compose.ui)
    implementation(rootProject.libs.hilt.android)
    ksp(rootProject.libs.hilt.compiler)

    testImplementation(rootProject.libs.junit5)
    testImplementation(rootProject.libs.test.mockk)
    testImplementation(rootProject.libs.coroutines.test)
    testImplementation(rootProject.libs.test.core.ktx)

    implementation("androidx.work:work-runtime:2.9.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")


    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")


    implementation("com.google.accompanist:accompanist-swiperefresh:0.34.0")
}
