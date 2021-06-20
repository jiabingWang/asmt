package com.sixsixsix.asmt.manager

import com.sixsixsix.asmt.bean.ViewNode


/**
 * @author ZhaoYun
 * @package_name com.wait.translationball.translate.view.ontouch
 * @date 2020/12/26
 * desc：获取翻译内容操作
 */
interface OnTouchStrategy {
    fun onDown()
    fun onMove(x: Int, y: Int)
    fun onUp(viewNode: ViewNode = ViewNode.EMPTY_NODE)
    fun onSingleTapUp()
    fun onLongPress()
}