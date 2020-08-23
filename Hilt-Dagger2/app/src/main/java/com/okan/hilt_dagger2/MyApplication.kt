package com.okan.hilt_dagger2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Every app uses hilt should use an Application class as HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

}