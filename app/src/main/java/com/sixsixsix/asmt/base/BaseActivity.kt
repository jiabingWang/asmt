package com.sixsixsix.asmt.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * 又是一年秋
 * @author  Wait and wait
 * 时间: on 2020/9/22
 * 描述：
 */
abstract class BaseActivity<T :ViewBinding> : AppCompatActivity() {
    lateinit var binding :T
    /**
     * 动态权限请求码
     */
    private val REQUEST_CODE_PERMISSION = 1995

    /**
     * 动态权限的回调，是否有此权限
     * 动态权限的回调，是否勾选了不再提示
     */
    private var needPermissionCall: ((granted: Boolean, checked: Boolean) -> Unit)? = null

    abstract fun initData()
    abstract fun initView()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val superclass: Type? = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method=aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as T
            setContentView(binding.root)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        initData()
        initView()
    }

    protected fun getPermission(
        permission: MutableList<String>,
        needPermissionCall: (granted: Boolean, checked: Boolean) -> Unit
    ) {
        var isNeedRequest = false
        val list = mutableListOf<String>()
        permission.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                isNeedRequest = true
                list.add(it)
            }
        }
        if (isNeedRequest) {
            this.needPermissionCall = needPermissionCall
            if (list.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    list.toTypedArray(),
                    REQUEST_CODE_PERMISSION
                )
            } else {
                needPermissionCall.invoke(true, false)
            }
        } else {
            needPermissionCall.invoke(true, false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            var isGetAllPermission = true
            var checked = false
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGetAllPermission = false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permissions[i]
                        )
                    ) {
                        checked = true
                    }
                }
            }
            needPermissionCall?.invoke(isGetAllPermission, checked)
        }
    }
}