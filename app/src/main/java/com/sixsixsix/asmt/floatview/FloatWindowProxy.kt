package com.sixsixsix.asmt.floatview

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import com.sixsixsix.asmt.App

/**
 * 悬浮窗基类
 * @author : jiaBing
 */
class FloatWindowProxy {

    /**
     * 窗口管理器
     */
    val mWindowManager = App.sApp.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    /**
     * 悬浮窗的布局参数
     */
    val mParams = WindowManager.LayoutParams().apply {
        type = if (Build.VERSION.SDK_INT >= 26) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        format = PixelFormat.RGBA_8888
        //FLAG_LAYOUT_IN_SCREEN
        flags = FLAG_NOT_TOUCH_MODAL or FLAG_NOT_FOCUSABLE or FLAG_LAYOUT_IN_SCREEN
        gravity = Gravity.LEFT or Gravity.TOP
        x = 0
        y = 0
        width = WRAP_CONTENT
        height = WRAP_CONTENT
    }


}