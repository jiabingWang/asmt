package com.sixsixsix.asmt.fragment

import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.content.ContextCompat
import com.sixsixsix.asmt.App
import com.sixsixsix.asmt.R
import com.sixsixsix.asmt.base.BaseFragment
import com.sixsixsix.asmt.databinding.FragmentFeaturesBinding
import com.sixsixsix.asmt.manager.DataStoreManager
import com.sixsixsix.asmt.manager.FloatViewMediator
import com.sixsixsix.asmt.service.AutoClickService
import com.sixsixsix.asmt.util.*
import com.sixsixsix.asmt.util.floatwindowpermission.FloatWindowPermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :
 */
@AndroidEntryPoint
class FeaturesFragment : BaseFragment<FragmentFeaturesBinding>() {
    companion object {
        fun newInstance() = FeaturesFragment()
    }

    /**
     * 辅助功能权限
     */
    private var mPermissionAccessibility = false

    /**
     * 悬浮窗权限
     */
    private var mPermissionFloatWindow = false

    override fun processUI() {

        binding.tvAccessibilityPermissionsStatus.setOnClickListener {
            if (mPermissionAccessibility) {
                toast("您已有权限")
            } else {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                })
            }
        }
        binding.tvFloatingWindowPermissionsStatus.setOnClickListener {
            if (mPermissionFloatWindow) {
                toast("您已有权限")
            } else {
                FloatWindowPermissionUtil.applyPermission(requireActivity())
            }
        }
        binding.tvStart.setOnClickListener {
            GlobalScope.launch {
                LogUtil.logD("我的任务--$${DataStoreManager.getChannelList()}")
            }

            if (mPermissionAccessibility && mPermissionFloatWindow) {
                FloatViewMediator.start(requireActivity())
                //开始循环判断时间
                AutoClickService.sAutoClickService?.startAutoClick()
            } else {
                toast("没有权限")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPermissionAccessibility = isAccessibilitySettingsOn(App.sApp)
        binding.tvAccessibilityPermissionsStatus.apply {
            if (mPermissionAccessibility) {
                text = getString(R.string.open)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            } else {
                text = getString(R.string.close)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
        mPermissionFloatWindow = FloatWindowPermissionUtil.checkPermission(requireContext())
        binding.tvFloatingWindowPermissionsStatus.apply {
            if (mPermissionFloatWindow) {
                text = getString(R.string.open)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            } else {
                text = getString(R.string.close)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }
}