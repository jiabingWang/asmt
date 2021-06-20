package com.sixsixsix.asmt.floatview

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.widget.AppCompatImageView
import com.sixsixsix.asmt.R
import com.sixsixsix.asmt.manager.OnViewImpl
import com.sixsixsix.asmt.util.LogUtil
import com.sixsixsix.asmt.util.screen

/**
 * @author : jiaBing
 * @date   : 2021/6/19
 * @desc   : 悬浮球
 */
class ClickBallView (context: Context) : AppCompatImageView(context) {
    /**
     * 悬浮窗代理类
     */
    val mWindowProxy = FloatWindowProxy()
    /**
     * 上一次的rawX
     */
    var mLastRawX = -1F

    /**
     * 上一次的rawX
     */
    var mLastRawY = -1F

    /**
     * 上一次用于分析的rawX
     */
    var mLastAnalyseRawX = -1F

    /**
     * 上一次用于分析的rawY
     */
    var mLastAnalyseRawY = -1F
    /**
     * 最小滑动距离
     */
    val mTouchSlop by lazy { ViewConfiguration.get(context).scaledTouchSlop }
    init {
        setImageResource(R.drawable.shape_ball)
    }
    /**
     * 创建视图
     */
    fun createWindow() {
        with(mWindowProxy) {
            mWindowManager.addView(this@ClickBallView, mParams.apply {
                measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
                width = measuredWidth
                height = measuredHeight
                x = screen().width
                y = screen().height / 4
            })
        }
    }
    /**
     * 是否处在右屏幕边缘
     */
    private fun isInSide() = mWindowProxy.mParams.x >= screen().width - width
    /**
     * 手势监听
     */
    private val mGestureDetector = GestureDetector(context, GestureListener())
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            OnViewImpl.onSingleTapUp(this@ClickBallView)
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent) {
            OnViewImpl.onLongPress(this@ClickBallView)
        }
    }
    /**
     * 键盘弹出
     * @param isPop true:弹出 false:收起
     */
    fun onKeyboardStatusChanged(isPop: Boolean) {
        if (isInSide()) {
            toSide(isPop)
        }
    }
    /**
     * 屏幕旋转
     */
    fun onScreenOrientationRotate(isKeyboardPop: Boolean) {
        toSide(isKeyboardPop)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                OnViewImpl.onDown(this, event.rawX, event.rawY)
            }

            MotionEvent.ACTION_MOVE -> {
                OnViewImpl.onMove(this, event.rawX, event.rawY)
            }

            MotionEvent.ACTION_UP -> {
                OnViewImpl.onUp(this, event.rawX, event.rawY)
            }
        }
        return true
    }
    /**
     * 更新悬浮球在屏幕中的位置。
     */
    fun updateBallPosition(offsetX: Float, offsetY: Float) {
        with(mWindowProxy) {
            mWindowManager.updateViewLayout(this@ClickBallView, mParams.apply {
                x += offsetX.toInt()
                y += offsetY.toInt()
                if (x <= 0) {
                    x = 0
                } else if (x >= screen().width - width) {
                    x = screen().width - width
                }
                if (y <= 0) {
                    y = 0
                } else if (y >= screen().height - height) {
                    y = screen().height - height
                }
            })
        }
    }
    /**
     * 悬浮球靠边
     * @param isKeyboardPop 键盘是否弹出
     * @param isPositionFree 是否固定位置
     */
    fun toSide(isKeyboardPop: Boolean, isPositionFree: Boolean = false) {
        with(mWindowProxy) {
            mWindowManager.updateViewLayout(this@ClickBallView, mParams.apply {
                x = screen().width
                y = when {
                    isPositionFree -> y
                    isKeyboardPop -> screen().height / 4
                    else -> screen().height / 2
                }
            })
        }
        //避免再截图隐藏后up，偏移动画看不到
        displayWindow()
    }
    fun removeWindow() {
        mWindowProxy.mWindowManager.removeView(this)
    }

    fun dismissWindow() {
        visibility = View.GONE
    }

    fun displayWindow() {
        visibility = View.VISIBLE
    }
    /**
     * 回收
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        //移除附带的window
        with(mWindowProxy) {

        }
    }
}