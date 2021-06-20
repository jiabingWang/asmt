package com.sixsixsix.asmt.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sixsixsix.asmt.App
import com.sixsixsix.asmt.manager.FloatViewMediator
import com.sixsixsix.asmt.util.configuration

/**
 * 转屏广播接收器
 * @author : JiaBin
 **/
class RotateScreenReceiver : BroadcastReceiver() {

    /**
     * 缓存屏幕方向
     */
    var currentOrientation = App.sApp.configuration.orientation

    override fun onReceive(context: Context, intent: Intent?) {

        if (currentOrientation == context.configuration.orientation) {
            return
        } else {
            //转屏
            currentOrientation = context.configuration.orientation
            FloatViewMediator.rotateScreenOrientation(context)
        }
    }
}