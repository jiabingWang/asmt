/**

 * @author  Wait and wait
 * 时间: on 2020/9/22
 * 描述：
 */
object Versions {
    const val compileSdkVersion = 28
    const val minSdkVersion = 21
    const val targetSdkVersion = 28
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val ktx_core = "1.3.1"
    const val kotlin_version = "1.3.72"
    const val appcompat = "1.2.0"
    const val material = "1.0.0-rc01"
    const val constraintLayout = "2.0.1"
    const val nav_version = "2.3.0"
    const val okhttp = "3.11.0"
    const val gson = "2.8.5"
    const val anko = "0.10.8"
    const val coroutines_android = "1.3.9"
    const val coroutines_core = "1.4.1"
    const val retrofit = "2.7.1"
    const val coroutines_adapter = "0.9.2"
    const val logging_interceptor = "4.3.0"
    const val lifecycle_viewmodel_ktx = "2.3.0-alpha07"
    const val datastore_preferences = "1.0.0-alpha05"
    const val hilt_android = "2.28-alpha"
    const val hilt_lifecycle_viewmodel = "1.0.0-alpha01"
    const val base_recycler_helper = "3.0.2"
    const val rxpermissions ="0.12"
    const val glide ="4.12.0"
}

object Deps {
    //kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    const val ktx_core = "androidx.core:core-ktx:${Versions.ktx_core}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_core}"

    //androidx


    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"

    //  //jetpack
    const val nav_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_viewmodel_ktx}"
    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_viewmodel_ktx}"
    const val lifecycle_livedata_ktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_viewmodel_ktx}"
    const val datastore_preferences =
        "androidx.datastore:datastore-preferences:${Versions.datastore_preferences}"
    const val datastore_preferences_core =
        "androidx.datastore:datastore-preferences-core:${Versions.datastore_preferences}"


    //net
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutines_adapter}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"

    //hilt
    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt_android}"
    const val hilt_android_compiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"
    const val hilt_lifecycle_viewmodel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_lifecycle_viewmodel}"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_lifecycle_viewmodel}"
    const val base_recycler_helper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.base_recycler_helper}"
    //other
    const val rxpermissions ="com.github.tbruyelle:rxpermissions:${Versions.rxpermissions}"
    const val glide ="com.github.bumptech.glide:glide:${Versions.glide}"
}
