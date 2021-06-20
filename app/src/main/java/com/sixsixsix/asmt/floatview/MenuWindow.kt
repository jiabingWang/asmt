package com.sixsixsix.asmt.floatview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.sixsixsix.asmt.R
import com.sixsixsix.asmt.service.AutoClickService
import com.sixsixsix.asmt.util.screen
import kotlinx.android.synthetic.main.float_window_menu.view.*

/**
 * @author : jiaBing
 * @date   : 2021/6/20
 * @desc   :菜单功能控制
 */
class MenuWindow(context: Context) : LinearLayout(context), IFloatWindow {
    /**
     * 悬浮窗代理类
     */
    private val mWindowProxy = FloatWindowProxy()

    init {

        LayoutInflater.from(context).inflate(
            R.layout.float_window_menu, this
        )
        tv_click.setOnClickListener {
            AutoClickService.sAutoClickService?.onClick()
        }
    }

    override fun createWindow() {
        with(mWindowProxy) {
            mWindowManager.addView(this@MenuWindow, mParams.apply {
                measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
                width = measuredWidth
                height = measuredHeight
                x = screen().width
                y = screen().height
            })
        }
    }

    override fun dismissWindow() {
        visibility = View.INVISIBLE
    }
}