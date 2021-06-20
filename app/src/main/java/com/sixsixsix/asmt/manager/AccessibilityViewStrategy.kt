package com.sixsixsix.asmt.manager

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat.animate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sixsixsix.asmt.floatview.ClickBallView
import kotlin.math.abs

/**
 * @author : jiaBing
 * @date   : 2020/12/30 10:53
 * @desc   : 辅助功能模式下，View的操作
 */
class AccessibilityViewStrategy : OnViewStrategy {


    override fun onDown(
        clickBallView: ClickBallView, x: Float, y: Float
    ) {
        //取消动画
        animate(clickBallView).cancel()
        //取消偏移
        clickBallView.translationX = 0F
        //重新获取屏幕宽高
        //当前是否有辅助功能权限、或者辅助服务异常未连接
        LocalBroadcastManager.getInstance(clickBallView.context)
            .sendBroadcast(Intent("ACCESSIBILITY").apply {
                putExtra("action", "down")
            })
        clickBallView.mLastRawX = x
        clickBallView.mLastRawY = y
        clickBallView.mLastAnalyseRawX = x
        clickBallView.mLastAnalyseRawY = y
    }

    override fun onMove(clickBallView: ClickBallView, x: Float, y: Float) {
        //取消偏移
        clickBallView.translationX = 0F
        /**
         * 更新悬浮球的位置
         */
        fun updateBallPosition() {

            clickBallView.updateBallPosition(
                x - clickBallView.mLastRawX,
                y - clickBallView.mLastRawY
            )

            clickBallView.mLastRawX = x
            clickBallView.mLastRawY = y
        }
        updateBallPosition()
        val currentX = clickBallView.mWindowProxy.mParams.x
        val currentY = clickBallView.mWindowProxy.mParams.y

        //最小滑动距离判断
        val moveX = x - clickBallView.mLastAnalyseRawX
        val moveY = y - clickBallView.mLastAnalyseRawY

        val shouldAnalyse =
            abs(moveX) >= clickBallView.mTouchSlop || abs(moveY) >= clickBallView.mTouchSlop
        if (shouldAnalyse) {
            clickBallView.mLastAnalyseRawX = x
            clickBallView.mLastAnalyseRawY = y
        }
        //非长按时
        if (shouldAnalyse) {
            LocalBroadcastManager.getInstance(clickBallView.context)
                .sendBroadcast(Intent("ACCESSIBILITY").apply {
                    putExtra("action", "move")
                    putExtra("x", currentX)
                    putExtra("y", currentY)
                })
        }
    }

    override fun onUp(clickBallView: ClickBallView, x: Float, y: Float) {
        LocalBroadcastManager.getInstance(clickBallView.context)
            .sendBroadcast(Intent("ACCESSIBILITY").apply {
                putExtra("action", "up")
            })

        //悬浮球归位
//        clickBallView.toSide(FloatViewMediator.isKeyboardPop, true)
    }

    override fun onSingleTapUp(clickBallView: ClickBallView) {
    }

    override fun onLongPress(clickBallView: ClickBallView) {
        Log.d("jiaBing", "onLongPress: ")
        //靠边时才处理长按事件
    }
}