package com.sixsixsix.asmt.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :
 */
abstract class BaseBindingAdapter<I, VB : ViewBinding> :
    BaseQuickAdapter<I, BaseBindingAdapter.VBViewHolder<VB>>(0) {


    class VBViewHolder<VB : ViewBinding>(val vb: VB, val view: View) : BaseViewHolder(view) {
        fun getVB() = vb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder<VB> {
        var binding: VB? = null
        val superclass: Type? = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, LayoutInflater.from(parent.context)) as VB

        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return VBViewHolder(binding!!, binding.root)
    }

    override fun convert(holder: VBViewHolder<VB>, item: I) {

    }
}
