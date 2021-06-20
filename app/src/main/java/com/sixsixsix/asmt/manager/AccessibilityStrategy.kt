package com.sixsixsix.asmt.manager

import com.sixsixsix.asmt.bean.ViewNode
import com.sixsixsix.asmt.service.AutoClickService


/**
 * @author ZhaoYun
 * @date 2020/12/26
 * desc： 辅助权限情况下的处理
 */
class AccessibilityStrategy : OnTouchStrategy {
    override fun onDown() {
        AutoClickService.sAutoClickService?.onActionDown()
    }

    override fun onMove(x: Int, y: Int) {
        AutoClickService.sAutoClickService?.onActionMove(x, y)
    }

    override fun onUp(viewNode: ViewNode) {
        AutoClickService.sAutoClickService?.onActionUp()
    }

    override fun onSingleTapUp() {

    }

    override fun onLongPress() {

    }

}