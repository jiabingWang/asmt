package com.sixsixsix.asmt

import android.app.Application
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sixsixsix.asmt.manager.*
import com.sixsixsix.asmt.receiver.ClickBallReceiver
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        GlobalScope.launch {
            DataStoreManager.create(sApp,packageName)
        }
        receiver = ClickBallReceiver()
        OnTouchImpl.setStrategy(AccessibilityStrategy())
        OnViewImpl.setStrategy(AccessibilityViewStrategy())
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver!!, IntentFilter("ACCESSIBILITY"))
    }
}