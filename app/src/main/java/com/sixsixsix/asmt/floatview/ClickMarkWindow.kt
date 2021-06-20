package com.sixsixsix.asmt.floatview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.sixsixsix.asmt.R
import com.sixsixsix.asmt.bean.ViewNode
import kotlin.math.abs

/**
 * @author : jiaBing
 * @date   : 2021/6/19
 * @desc   :
 */
class ClickMarkWindow (context: Context) : LinearLayout(context) {
    /**
     * 悬浮窗代理类
     */
    private val mWindowProxy = FloatWindowProxy()
    init {
        LayoutInflater.from(context).inflate(R.layout.float_window_mark, this, true)
    }

    /**
     * 创建
     */
    fun createWindow() {
        with(mWindowProxy) {
            mWindowManager.addView(this@ClickMarkWindow, mParams)
        }
    }
    /**
     * 隐藏
     */
    fun dismissWindow() {
        visibility = View.INVISIBLE
    }
    /**
     * 显示
     */
    fun upDataWindow(viewNode: ViewNode?) {
        Log.d("jiaBing", "更新标记位--$viewNode")
        visibility = View.VISIBLE
        with(mWindowProxy) {
            mWindowManager.updateViewLayout(this@ClickMarkWindow, mParams.apply {
                //todo 向上，向左移的时候出现left比right小的情况  bottom和top也是  需要处理
                x = viewNode!!.bound.left
                y = viewNode.bound.top
                width = abs(viewNode.bound.right - viewNode.bound.left)
                height = abs(viewNode.bound.bottom - viewNode.bound.top)
            })
        }
    }
    /**
     * 回收window
     */
    fun removeWindow() {
        if (parent != null) {
            mWindowProxy.mWindowManager.removeView(this)
        }
    }

}