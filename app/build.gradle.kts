plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.frameapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.frameapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")


//    implementation ("com.google.ar.sceneform.ux:sceneform-ux:1.17.1")
//    implementation ("com.google.ar.sceneform:sceneform-assets:1.17.1")
//    implementation ("com.google.ar:core:1.37.0")
//
//    implementation("com.github.SceneView:sceneform:1.21.0") // Sceneform Alternative

//    implementation("com.google.ar:core:1.31.0")
//
//    // SceneView (Replacement for Sceneform)
//    implementation("io.github.sceneview:sceneview:2.2.1")
//
//    // Filament Rendering Engine
//    implementation("com.google.android.filament:filament-android:1.21.1")
//    implementation("com.google.android.filament:gltfio-android:1.21.1")

//        implementation("io.github.sceneview:sceneview:2.2.1")
//        implementation("androidx.appcompat:appcompat:1.6.1")
//        implementation("androidx.activity:activity-ktx:1.8.2")
//        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//        implementation("com.google.ar:core:1.40.0")
//        implementation("com.google.ar.sceneform.ux:sceneform-ux:1.20.0")  // Sceneform for rendering

//    dependencies {
//    implementation("io.github.sceneview:sceneview:2.2.1")
//    implementation("com.google.ar:core:1.40.0")
//    implementation("com.google.android.filament:filament-android:1.38.0") // For texture rendering
//    }

    implementation ("com.google.ar:core:1.37.0")
//    implementation ("com.google.ar.sceneform:sceneform:1.17.1")
//    implementation ("com.google.ar.sceneform:sceneform-ux:1.17.1")
    implementation ("com.gorisse.thomas.sceneform:sceneform:1.23.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.picasso:picasso:2.8")

}