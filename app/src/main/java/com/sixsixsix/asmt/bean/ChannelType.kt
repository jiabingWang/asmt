package com.sixsixsix.asmt.bean

import androidx.annotation.DrawableRes
import com.sixsixsix.asmt.R

enum class ChannelType(val channel: String, @DrawableRes val icon: Int) {
    JD("京东", R.drawable.ic_jd),
    SN("苏宁", R.drawable.ic_sn),
    TM("天猫超市", R.drawable.ic_tb),
    WY("网易严选", R.drawable.ic_wy)
}