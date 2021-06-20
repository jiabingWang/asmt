package com.sixsixsix.asmt.manager

import com.sixsixsix.asmt.floatview.ClickBallView

/**
 * @author : jiaBing
 * @date   : 2020/12/30 10:57
 * @desc   : 处理View的状态
 */
object OnViewImpl {
    lateinit var onViewStrategy: OnViewStrategy
    fun setStrategy(onViewStrategy: OnViewStrategy) {
        this.onViewStrategy = onViewStrategy
    }

    fun onDown(clickBallView: ClickBallView, x: Float, y: Float) {
        onViewStrategy.onDown(clickBallView, x, y)
    }

    fun onMove(clickBallView: ClickBallView, x: Float, y: Float) {
        onViewStrategy.onMove(clickBallView, x, y)
    }

    fun onUp(clickBallView: ClickBallView, x: Float, y: Float) {
        onViewStrategy.onUp(clickBallView, x, y)
    }

    fun onSingleTapUp(clickBallView: ClickBallView) {
        onViewStrategy.onSingleTapUp(clickBallView)
    }

    fun onLongPress(clickBallView: ClickBallView) {
        onViewStrategy.onLongPress(clickBallView)
    }
}