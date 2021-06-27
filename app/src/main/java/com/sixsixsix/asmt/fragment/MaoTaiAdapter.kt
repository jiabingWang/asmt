package com.sixsixsix.asmt.fragment

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sixsixsix.asmt.R
import com.sixsixsix.asmt.bean.MaoTaiItemBean
import com.sixsixsix.asmt.util.toast
import javax.inject.Inject

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :
 */
class MaoTaiAdapter @Inject constructor() :
    BaseQuickAdapter<MaoTaiItemBean, BaseViewHolder>(R.layout.item_channel) {
    override fun convert(holder: BaseViewHolder, item: MaoTaiItemBean) {
        with(holder) {
            val ivIcon=getView<ImageView>(R.id.iv_icon)
            val tvTime=getView<TextView>(R.id.tv_time)
            val tvGo=getView<TextView>(R.id.tv_go)
            Glide.with(context).load(item.channelType.icon).into(ivIcon)
            tvTime.text = item.time
            tvGo.setOnClickListener {
                toast("去APP")
            }
        }

    }
}