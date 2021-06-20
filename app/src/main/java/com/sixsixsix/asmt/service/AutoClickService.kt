package com.sixsixsix.asmt.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.MainThread
import com.sixsixsix.asmt.DelayRecognizeProxy
import com.sixsixsix.asmt.bean.ViewNode
import com.sixsixsix.asmt.bean.ViewNode.Companion.EMPTY_NODE
import com.sixsixsix.asmt.manager.FloatViewMediator
import com.sixsixsix.asmt.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * @author : jiaBing
 * @date   : 2021/6/17
 * @desc   :
 */
@AndroidEntryPoint
class AutoClickService : AccessibilityService() {
    companion object {
        var sAutoClickService: AutoClickService? = null
    }

    /**
     * 当前的节点 - 初始化为空节点
     */
    private var mCurrentViewNode: ViewNode = EMPTY_NODE

    /**
     * 所有的树叶节点
     */
    private var mNodeList = ConcurrentLinkedQueue<ViewNode>()

    /**
     * 延迟任务的代理
     */
    private val mDelayRecognizeProxy = DelayRecognizeProxy()


    /**
     * 按下悬浮球
     */
    @MainThread
    fun onActionDown() {
        FloatViewMediator.dismissMarkWindow()
        LogUtil.logD("onActionDown")
        fetchLeafViewNodeList()
    }

    /**
     * 松开悬浮球
     */
    @MainThread
    fun onActionUp() {
        //取消高亮
//        mCurrentViewNode = EMPTY_NODE
        //取消延迟任务
        mDelayRecognizeProxy.cancelLastTask()
        FloatViewMediator.dismissMarkWindow()
    }

    /**
     * 移动 - 耗时1ms
     */
    fun onActionMove(x: Int, y: Int) {
        LogUtil.logD("X---${x} ---Y--${y}")
        /**
         * 遍历节点树，找到节点 - 耗时1ms
         * @param unFindCallback 未进入节点回调
         * @param findCallback 进入节点回调
         * @return 当前节点 null：未进入节点
         */
        @MainThread
        fun traverseViewNode(
            x: Int,
            y: Int,
            unFindCallback: () -> Unit,
            findCallback: (node: ViewNode) -> Unit
        ) {
            var tempNode: ViewNode? = null

            if (mNodeList.isEmpty()) {
                //节点树为空
                LogUtil.logD("节点树为空")
                unFindCallback.invoke()
                return
            }
            for (everyNode in mNodeList) {
                //找到节点
                if (isInNode(x, y, everyNode.bound)) {
                    if (tempNode == null) {
                        tempNode = everyNode
                    } else {
                        if (everyNode.bound.contains(tempNode.bound)) {
                            //抛弃大的
                            //do nothing
                        } else {
                            tempNode = everyNode
                        }
                    }
                }
            }
            if (tempNode == null) {
                //未找到节点
                unFindCallback.invoke()
            } else {
                findCallback.invoke(tempNode)
            }

        }
        //开始滑动
        onStartMove()
        traverseViewNode(x, y, {
            //未找到
            if (mCurrentViewNode == EMPTY_NODE) {
                //依然未找到
                //do nothing
                LogUtil.logD("依然未找到")
            } else {
                //取消延迟任务
                LogUtil.logD("离开上一次的节点")
                mDelayRecognizeProxy.cancelLastTask()
                //离开上一次的节点
                mCurrentViewNode = EMPTY_NODE
                onMoveOutNode()
            }
        }) {
            //找到
            if (mCurrentViewNode == EMPTY_NODE) {
                //刚进入节点
                LogUtil.logD("刚进入节点")
                fun realDo() {
                    if (mCurrentViewNode.bound.top == 0 && mCurrentViewNode.bound.left == 0) {

                    } else {
                        onMoveInNode()
                        //开始延迟任务
                        mDelayRecognizeProxy.startNewTask {
                            LogUtil.logD("做事情")
                        }
                    }

                }

                if (it.isBigNode) {
                    LogUtil.logD("isBigNode")
                    mCurrentViewNode = it
                    mDelayRecognizeProxy.startNewTask {
                        runOnUiThread {
                            realDo()
                        }
                    }
                } else {
                    LogUtil.logD("isBigNode.not")
                    mCurrentViewNode = it
                    realDo()
                }
            } else {
                //不是刚进入节点
                LogUtil.logD("1111不是刚进入节点")
                if (it == mCurrentViewNode) {
                    //和上次是同一个，说明没有移动出去
                    LogUtil.logD("说明没有移动出去")
                    //do nothing
                } else {
                    //和上次不是同一个，说明移动出去了
                    LogUtil.logD("和上次不是同一个")
                    fun realDo() {
                        onMoveInNode()
                        //重新开始延迟任务
                        mDelayRecognizeProxy.startNewTask { LogUtil.logD("做事情") }
                    }
                    if (it.isBigNode) {
                        //取消延迟任务
                        LogUtil.logD("22222")
                        mDelayRecognizeProxy.cancelLastTask()
                        onMoveOutNode()
                        mCurrentViewNode = it

                        mDelayRecognizeProxy.startNewTask {
                            runOnUiThread {
                                realDo()
                            }
                        }
                    } else {
                        LogUtil.logD("33333")
                        //取消延迟任务
                        mDelayRecognizeProxy.cancelLastTask()
                        onMoveOutNode()
                        mCurrentViewNode = it
                        realDo()
                    }
                }
            }
        }
    }

    /**
     * 开始滑动
     */
    @MainThread
    private fun onStartMove() {

    }

    /**
     * 进入节点
     */
    @MainThread
    private fun onMoveInNode() {
        //显示高亮
        FloatViewMediator.updateMarkView(this, mCurrentViewNode)
    }

    /**
     * 离开节点
     */
    @MainThread
    private fun onMoveOutNode() {
        //取消高亮
        FloatViewMediator.dismissMarkWindow()
    }


    /**
     * 监听辅助功能事件
     */
    @MainThread
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                LogUtil.logD("onAccessibilityEvent")
                fetchLeafViewNodeList()
                FloatViewMediator.dismissMarkWindow()
            }
        }
    }

    /**
     * 服务开始
     */
    @MainThread
    override fun onServiceConnected() {
        super.onServiceConnected()
        LogUtil.logD("onServiceConnected: ")
        //获取设备上所有APP包名，加入辅助功能名单
        // todo 处理"获取应用信息的权限"被关闭（低优先级）
        serviceInfo.packageNames = installedAppList(packageManager)
        isAccessibilityServiceConnected = true
        sAutoClickService = this
    }

    override fun onInterrupt() {}

    override fun onUnbind(intent: Intent?): Boolean {
        mDelayRecognizeProxy.terminate()
        isAccessibilityServiceConnected = false
        sAutoClickService = null
        return super.onUnbind(intent)
    }


    /**
     * 判断当前点是都在矩形范围内
     */
    private fun isInNode(x: Int, y: Int, rect: Rect): Boolean =
        rect.let { x in it.left..it.right && y in it.top..it.bottom }

    /**
     * 遍历整个屏幕，获取所有的树叶节点
     */
    private fun fetchLeafViewNodeList() {

        //edit
        if (rootInActiveWindow == null) {
            return
        }
        LogUtil.logD("mNodeList.clear()")
        mNodeList.clear()

        val bgImageViewNodeList = ArrayList<AccessibilityNodeInfo>()
        val otherViewNodeList = LinkedList<AccessibilityNodeInfo>()

        /**
         * 将node添加入子节点
         */
        fun addNodeToList(bound: Rect, nodeInfo: AccessibilityNodeInfo, isBigNode: Boolean) {
            val contentDescription = nodeInfo.contentDescription?.toString()
            var content = nodeInfo.text?.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (content == null) {
                    //EditText
                    content = nodeInfo.hintText?.toString()
                }
            }
            //添加到列表中
            LogUtil.logD("添加到列表中${bound.toString()}")
            mNodeList.offer(ViewNode(bound, content, contentDescription, isBigNode,nodeInfo))
        }

        /**
         * 获取所有树叶节点
         */
        fun doFetchLeafViewNodeList(
            nodeList: ConcurrentLinkedQueue<ViewNode>,
            nodeInfo: AccessibilityNodeInfo?
        ) {

            /**
             * 判断是否为ViewGroup
             */
            fun isViewGroup(nodeInfo: AccessibilityNodeInfo): Boolean {
                if (nodeInfo.className == "android.widget.RelativeLayout"
                    || nodeInfo.className == "android.widget.FrameLayout"
                    || nodeInfo.className == "android.widget.RelativeLayout"
                    || nodeInfo.className == "android.widget.LinearLayout"
                    || nodeInfo.className == "android.widget.ConstraintLayout"
                    || nodeInfo.className == "android.widget.ViewGroup"
                ) {
                    return true
                }
                return false
            }
            nodeInfo?.let {
                if (0 == nodeInfo.childCount) {
                    if (isViewGroup(nodeInfo)) {
                        //树叶节点不允许为ViewGroup
                        return@let
                    }

                    val bound = Rect()
                    nodeInfo.getBoundsInScreen(bound)
                    if (bound.left > 0 && bound.top > 0) {
                        //普通节点

                        addNodeToList(bound, nodeInfo, false)

                        otherViewNodeList.add(nodeInfo)
                    } else {
                        //大节点，可能包含中间有其他小节点
                        bgImageViewNodeList.add(nodeInfo)
                    }
                } else {
                    //非树叶节点、继续递归
                    for (i in 0 until nodeInfo.childCount) {
                        LogUtil.logD("继续递归--${nodeInfo.getChild(i)}")
                        doFetchLeafViewNodeList(nodeList, nodeInfo.getChild(i))
                    }
                }
            }
        }

        doAsync {
            LogUtil.logD("doAsync")
            if (rootInActiveWindow.packageName == "com.tencent.mm" && rootInActiveWindow.getChild(0).childCount == 2) {
                //在微信的聊天界面
                for (i in 0 until rootInActiveWindow.getChild(0).childCount) {
                    if (rootInActiveWindow.getChild(0).getChild(i).getChild(0)
                            .getChild(0).className == "android.widget.FrameLayout"
                    ) {
                        //第三层为FrameLayout的是聊天布局
                        doFetchLeafViewNodeList(
                            mNodeList,
                            rootInActiveWindow.getChild(0).getChild(i)
                        )
                    }
                }
            } else {
                doFetchLeafViewNodeList(mNodeList, rootInActiveWindow)
            }

            for (everyBigImageNode in bgImageViewNodeList) {
                val bigImageBound = Rect()
                everyBigImageNode.getBoundsInScreen(bigImageBound)

                for (everyOtherNode in otherViewNodeList) {
                    val otherBound = Rect()
                    everyOtherNode.getBoundsInScreen(otherBound)
                    if (bigImageBound.contains(otherBound)) {
                        //中间包含小节点

                        addNodeToList(bigImageBound, everyBigImageNode, true)
                        break
                    }
                }
                //中间没有包含小节点
                addNodeToList(bigImageBound, everyBigImageNode, false)
            }
        }
    }
    fun onClick(){
        LogUtil.logD("模拟点击--${mCurrentViewNode.bound}")
        mCurrentViewNode.nodeInfo?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }
}