package com.sixsixsix.asmt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 又是一年秋
 * @author  Wait and wait
 * 时间: on 2020/9/22
 * 描述：
 */
abstract class BaseFragment<T : ViewBinding> : Fragment(), CoroutineScope by MainScope() {
    lateinit var binding: T


    abstract fun processUI()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        val superclass: Type? = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as T
            view = binding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processUI()
    }
}