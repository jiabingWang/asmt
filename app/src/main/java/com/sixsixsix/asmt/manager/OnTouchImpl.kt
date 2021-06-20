package com.sixsixsix.asmt.manager

import com.sixsixsix.asmt.bean.ViewNode
import com.sixsixsix.asmt.bean.ViewNode.Companion.EMPTY_NODE

/**
 * @author ZhaoYun
 * @date 2020/12/26
 * desc：
 */
object OnTouchImpl {
    lateinit var onTouchStrategy: OnTouchStrategy

    fun setStrategy(onTouchStrategy: OnTouchStrategy) {
        this.onTouchStrategy = onTouchStrategy
    }

    fun onDown() {
        onTouchStrategy.onDown()
    }

    fun onMove(x: Int, y: Int) {
        onTouchStrategy.onMove(x, y)
    }

    fun onUp(viewNode: ViewNode = EMPTY_NODE) {
        onTouchStrategy.onUp(viewNode)
    }

    fun onSingleTapUp() {
        onTouchStrategy.onSingleTapUp()
    }

    fun onLongPress() {
        onTouchStrategy.onLongPress()
    }
}