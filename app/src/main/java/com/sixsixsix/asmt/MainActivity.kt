package com.sixsixsix.asmt

import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.sixsixsix.asmt.base.BaseActivity
import com.sixsixsix.asmt.databinding.ActivityMainBinding
import com.sixsixsix.asmt.manager.FloatViewMediator
import com.sixsixsix.asmt.util.floatwindowpermission.FloatWindowPermissionUtil
import com.sixsixsix.asmt.util.isAccessibilitySettingsOn
import com.sixsixsix.asmt.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    /**
     * 辅助功能权限
     */
    private var mPermissionAccessibility = false

    /**
     * 悬浮窗权限
     */
    private var mPermissionFloatWindow = false


    override fun initData() {

    }

    override fun initView() {
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
                FloatWindowPermissionUtil.applyPermission(this)
            }
        }
        binding.tvStart.setOnClickListener {
            if (mPermissionAccessibility && mPermissionFloatWindow) {
                FloatViewMediator.start(this@MainActivity)
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
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
            } else {
                text = getString(R.string.close)
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
            }
        }
        mPermissionFloatWindow = FloatWindowPermissionUtil.checkPermission(this)
        binding.tvFloatingWindowPermissionsStatus.apply {
            if (mPermissionFloatWindow) {
                text = getString(R.string.open)
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
            } else {
                text = getString(R.string.close)
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
            }
        }
    }
}