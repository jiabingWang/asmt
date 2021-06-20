package com.sixsixsix.asmt

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import androidx.annotation.MainThread

/**
 * @author ZhaoYun
 * @date 2021/1/2
 * desc：
 */
class DelayRecognizeProxy(val delayMillis:Long =200) {
    /**
     * 处理延迟任务
     */
    lateinit var mDelayHandler: Handler
    init {
        //初始化延迟线程
        mDelayHandler = Handler(HandlerThread("delay-task").apply {
            start()
        }.looper)
    }

    /**
     * 取消上一个延迟中任务
     */
    fun cancelLastTask() {
        mDelayHandler.removeMessages(1)
    }
    /**
     * 开始延迟任务
     */
    fun startNewTask(action:()->Unit) {

        val msg = Message.obtain(mDelayHandler) {
            action.invoke()
        }
        msg.what = 1
        mDelayHandler.sendMessageDelayed(msg, delayMillis)
    }

    /**
     * 回收
     */
    @MainThread
    fun terminate() {
        mDelayHandler.looper.quit()
    }
}