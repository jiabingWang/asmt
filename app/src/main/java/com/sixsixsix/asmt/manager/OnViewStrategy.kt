package com.sixsixsix.asmt.manager

import com.sixsixsix.asmt.floatview.ClickBallView

/**
 * @author ZhaoYun
 * @package_name com.wait.translationball.translate.view.ontouch
 * @date 2020/12/26
 * desc：对view的操作
 */
interface OnViewStrategy {
    fun onDown(clickBallView: ClickBallView, x: Float, y: Float)
    fun onMove(clickBallView: ClickBallView, x: Float, y: Float)
    fun onUp(clickBallView: ClickBallView, x: Float, y: Float)
    fun onSingleTapUp(clickBallView: ClickBallView)
    fun onLongPress(clickBallView: ClickBallView)
}