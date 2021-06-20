package com.sixsixsix.asmt.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.sixsixsix.asmt.bean.ViewNode
import com.sixsixsix.asmt.floatview.ClickBallView
import com.sixsixsix.asmt.floatview.ClickMarkWindow
import com.sixsixsix.asmt.floatview.MenuWindow
import com.sixsixsix.asmt.receiver.RotateScreenReceiver
import com.sixsixsix.asmt.util.reFetchScreen

/**
 * 悬浮控件中介者
 */
object FloatViewMediator {
    /**
     * 屏幕旋转广播接收器
     */
    private var rotateScreenReceiver: RotateScreenReceiver? = null

    /**
     * 键盘是否弹出
     */
    var isKeyboardPop = false
        private set

    /**
     * 悬浮球
     */
    @SuppressLint("StaticFieldLeak")
    var mBallWindow: ClickBallView? = null

    /**
     * 高亮区域
     */
    @SuppressLint("StaticFieldLeak")
    private var mMarkView: ClickMarkWindow? = null
    /**
     * 菜单浮窗
     */
    @SuppressLint("StaticFieldLeak")
    private var mMenuWindow: MenuWindow? = null

    fun start(context: Context) {
        registerRotateScreenReceiver(context)
        createClickBallView(context)
        createMenuWindow(context)
    }

    /**
     * 注册转屏广播接收器
     * 要监听这个系统广播就必须用这种方式来注册，不能再xml中注册，否则不能生效
     */
    private fun registerRotateScreenReceiver(context: Context) {
        rotateScreenReceiver = RotateScreenReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED)
        context.registerReceiver(rotateScreenReceiver, filter)
    }

    private fun createClickBallView(context: Context) {
        //显示悬浮球
        if (mBallWindow == null) {
            mBallWindow = ClickBallView(context)
            mBallWindow?.createWindow()
        }
        mBallWindow!!.isClickable = true
        mBallWindow!!.setOnClickListener {
            Log.d("jiaBing", "mBallWindowOnClick: ")
        }
    }
    private fun createMenuWindow(context: Context){
        if (mMenuWindow == null) {
            mMenuWindow = MenuWindow(context)
            mMenuWindow?.createWindow()
        }
        mMenuWindow!!.isClickable = true
    }
    /**
     * 更新内容高亮悬浮窗的位置和大小
     */
    fun updateMarkView(context: Context, viewNode: ViewNode) {
        if (null == mMarkView) {
            mMarkView = ClickMarkWindow(context)
            mMarkView?.createWindow()
        }
        mMarkView?.upDataWindow(viewNode)
    }

    /**
     * 隐藏高亮区域
     */
    fun dismissMarkWindow() {
        mMarkView?.dismissWindow()
    }

    /**
     * 旋转屏幕方向
     */
    fun rotateScreenOrientation(context: Context) {
        //重置屏幕宽高
        reFetchScreen()
        //小球归位
        mBallWindow?.onScreenOrientationRotate(isKeyboardPop)

    }

    /**
     * 回收所有
     * @param context applicationContext
     */
    fun terminate(context: Context) {
        /**
         * 注销转屏广播
         */
        fun unRegisterRotateScreenReceiver(context: Context) {
            rotateScreenReceiver?.let {
                context.unregisterReceiver(it)
                rotateScreenReceiver = null
            }
        }
        //注销转屏广播接收器
        unRegisterRotateScreenReceiver(context)
        //回收悬浮球
        mBallWindow?.removeWindow()
        mBallWindow = null
        //回收高亮区域
        mMarkView?.removeWindow()
        mMarkView = null
    }
}