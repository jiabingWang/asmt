/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.sixsixsix.asmt.util.floatwindowpermission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.sixsixsix.asmt.util.floatwindowpermission.rom.HuaweiUtils;
import com.sixsixsix.asmt.util.floatwindowpermission.rom.MeizuUtils;
import com.sixsixsix.asmt.util.floatwindowpermission.rom.MiuiUtils;
import com.sixsixsix.asmt.util.floatwindowpermission.rom.OppoUtils;
import com.sixsixsix.asmt.util.floatwindowpermission.rom.QikuUtils;
import com.sixsixsix.asmt.util.floatwindowpermission.rom.RomUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 悬浮窗权限
 *
 * @author zhaozp
 * @since 2016-10-17
 */

public class FloatWindowPermissionUtil {
    private static final String TAG = "FloatWindowPermission";

    public static boolean checkPermission(Context context) {
        //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                return miuiPermissionCheck(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                return meizuPermissionCheck(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                return huaweiPermissionCheck(context);
            } else if (RomUtils.checkIs360Rom()) {
                return qikuPermissionCheck(context);
            } else if (RomUtils.checkIsOppoRom()) {
                return oppoROMPermissionCheck(context);
            }
        }
        return commonROMPermissionCheck(context);
    }

    private static boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private static boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private static boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private static boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private static boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private static boolean commonROMPermissionCheck(Context context) {
        //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
        if (RomUtils.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        } else {
            Boolean result = true;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    //todo 防混淆
                    Class clazz = Settings.class;
                    Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                    result = (Boolean) canDrawOverlays.invoke(null, context);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            return result;
        }
    }

    public static void applyPermission(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                if (RomUtils.checkIsMiuiRom()) {
                    MiuiUtils.applyMiuiPermission(context);
                } else if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(context);
                } else if (RomUtils.checkIsHuaweiRom()) {
                    HuaweiUtils.applyPermission(context);
                } else if (RomUtils.checkIs360Rom()) {
                    QikuUtils.applyPermission(context);
                } else if (RomUtils.checkIsOppoRom()) {
                    OppoUtils.applyOppoPermission(context);
                }
            } else {
                if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(context);
                } else {
                    commonROMPermissionApplyInternal(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void commonROMPermissionApplyInternal(Context context) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = Settings.class;
        Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

        Intent intent = new Intent(field.get(null).toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
