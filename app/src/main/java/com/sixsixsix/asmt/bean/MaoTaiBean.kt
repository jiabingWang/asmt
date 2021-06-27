package com.sixsixsix.asmt.bean

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :茅台开抢平台和时间
 */
data class MaoTaiBean(val channelList: MutableList<MaoTaiItemBean>)
data class MaoTaiItemBean(val channelType: ChannelType, var time: String)