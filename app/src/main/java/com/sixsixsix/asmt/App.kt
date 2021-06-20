package com.sixsixsix.asmt

import android.app.Application
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sixsixsix.asmt.manager.AccessibilityStrategy
import com.sixsixsix.asmt.manager.AccessibilityViewStrategy
import com.sixsixsix.asmt.manager.OnTouchImpl
import com.sixsixsix.asmt.manager.OnViewImpl
import com.sixsixsix.asmt.receiver.ClickBallReceiver
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
    /**
     * 广播接收器
     */
    private var receiver: ClickBallReceiver? = null
    override fun onCreate() {
        super.onCreate()
        sApp = this
        receiver = ClickBallReceiver()
        OnTouchImpl.setStrategy(AccessibilityStrategy())
        OnViewImpl.setStrategy(AccessibilityViewStrategy())
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver!!, IntentFilter("ACCESSIBILITY"))
    }
}