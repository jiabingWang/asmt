package com.sixsixsix.asmt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 又是一年秋
 * @author  Wait and wait
 * 时间: on 2020/9/22
 * 描述：
 */
abstract class BaseFragment : Fragment() {

    abstract fun getLayoutResOrView(): Int

    abstract fun processUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResOrView(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processUI()
    }
}