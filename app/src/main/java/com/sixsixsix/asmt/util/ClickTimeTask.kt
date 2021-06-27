package com.sixsixsix.asmt.util

import java.util.*

/**
 * @author : jiaBing
 * @date   : 2021/6/21
 * @desc   : 定时器点击
 */
class ClickTimeTask(private val action: () -> Boolean?) : TimerTask() {
    override fun run() {
        action.invoke()
    }
}