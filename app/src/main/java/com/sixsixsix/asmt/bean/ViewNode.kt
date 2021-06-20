package com.sixsixsix.asmt.bean

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 辅助功能识别的节点信息
 * @author : JiaBin
 */
data class ViewNode(

    /**
     * 节点的范围
     */
    val bound: Rect,

    /**
     * 文本内容 - text
     */
    val content: String?,

    /**
     * 文本内容 - contentDescription
     */
    val description: String?,

    /**
     * 是否为包含其他节点的背景节点
     */
    val isBigNode: Boolean,
    val nodeInfo: AccessibilityNodeInfo? = null
) {

    companion object {

        /**
         * 空节点
         */
        val EMPTY_NODE by lazy {
            ViewNode(Rect(-1, -1, -1, -1), "", "", false, null)
        }
    }
}
