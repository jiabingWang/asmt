package com.sixsixsix.asmt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author : jiaBing
 * @date   : 2021/6/17
 * @desc   :
 */
@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var sApp: Application
    }

    override fun onCreate() {
        super.onCreate()
        sApp = this
    }
}