
package com.sixsixsix.asmt.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.sixsixsix.asmt.App
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * @author : jiaBing
 * @date   : 2021/6/19
 * @desc   :
 */
fun toast(t:String,duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(App.sApp, t, duration).show()
}
inline val Context.configuration: Configuration
    get() = resources.configuration
/**
 * 屏幕尺寸
 */
private var screen: Screen? = null
/**
 * 屏幕信息
 */
data class Screen(val width: Int, val height: Int, val density: Int)
fun screen(): Screen {
    if (screen == null) {
        val mWindowManager = App.sApp.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWindowManager.defaultDisplay.getRealMetrics(metrics)
        } else {
            val display = mWindowManager.defaultDisplay
            display.getMetrics(metrics)
        }

        screen = Screen(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi)
    }
    return screen!!
}
/**
 * 重新获取屏幕信息
 */
fun reFetchScreen() {
    screen = null
}
//以下为anko库中拷贝出来的代码
/**
 * Execute [f] on the application UI thread.
 */
fun Context.runOnUiThread(f: Context.() -> Unit) {
    if (Looper.getMainLooper() === Looper.myLooper()) f() else ContextHelper.handler.post { f() }
}
private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
}
private val crashLogger = { throwable : Throwable -> throwable.printStackTrace() }

class AnkoAsyncContext<T>(val weakRef: WeakReference<T>)
internal object BackgroundExecutor {
    var executor: ExecutorService =
        Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors())

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)

}

fun <T> T.doAsync(
    exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
    task: AnkoAsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = AnkoAsyncContext(WeakReference(this))
    return BackgroundExecutor.submit {
        return@submit try {
            context.task()
        } catch (thr: Throwable) {
            val result = exceptionHandler?.invoke(thr)
            if (result != null) {
                result
            } else {
                Unit
            }
        }
    }
}