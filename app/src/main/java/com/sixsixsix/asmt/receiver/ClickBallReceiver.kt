package com.sixsixsix.asmt.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import com.sixsixsix.asmt.bean.ViewNode
import com.sixsixsix.asmt.manager.OnTouchImpl

/**
 * @author ZhaoYun
 * @date 2021/6/17
 * desc：
 */
/**
 * 悬浮球动作广播接收器
 */
class ClickBallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        with(intent) {
            when (getStringExtra("action")) {
                "down" -> {
                    OnTouchImpl.onDown()
                }
                "move" -> {
                    val x = getIntExtra("x", 0)
                    val y = getIntExtra("y", 0)
                    OnTouchImpl.onMove(x, y)
                }

                "up" -> {
                    val rect: Rect? = getParcelableExtra("rect")
                    if (rect == null) {
                        OnTouchImpl.onUp()
                    } else {
                        val currentViewNode =
                            ViewNode(rect, null, null, false)
                        OnTouchImpl.onUp(currentViewNode)
                    }
                }

                else -> {

                }
            }
        }
    }
}