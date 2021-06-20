package com.sixsixsix.asmt.util

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.text.TextUtils
import com.sixsixsix.asmt.service.AutoClickService

/**
 * @author : jiaBing
 * @date   : 2021/6/17
 * @desc   : 辅助服务相关工具类
 */
/**
 * 判断是否有辅助功能权限
 */
/**
 * 辅助服务是否连接
 */
var isAccessibilityServiceConnected = false
fun isAccessibilitySettingsOn(mContext: Context): Boolean {
    var accessibilityEnabled = 0
    // TestService为对应的服务
    val service = mContext.packageName + "/" + AutoClickService::class.java.canonicalName
    try {
        accessibilityEnabled = Settings.Secure.getInt(mContext.applicationContext.contentResolver,
            android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
    } catch (e: Settings.SettingNotFoundException) {
    }

    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(mContext.applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        if (settingValue != null) {
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()

                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        }
    } else {
    }
    return false
}
/**
 * 获取所有已安装应用的数组
 */
fun installedAppList(packageManager: PackageManager) = packageManager.getInstalledPackages(0)
    .map { it.packageName }.toTypedArray()