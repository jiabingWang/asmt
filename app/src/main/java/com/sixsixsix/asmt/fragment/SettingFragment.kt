package com.sixsixsix.asmt.fragment

import android.app.TimePickerDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixsixsix.asmt.base.BaseFragment
import com.sixsixsix.asmt.databinding.FragmentSettingBinding
import com.sixsixsix.asmt.manager.DataStoreManager
import com.sixsixsix.asmt.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :
 */
@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    @Inject
    lateinit var mAdapter: MaoTaiAdapter


    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun processUI() {
        launch {
            var data = DataStoreManager.getChannelList()
            mAdapter.setList(data)
            rv_setting.layoutManager = LinearLayoutManager(requireContext())
            rv_setting.adapter = mAdapter
            mAdapter.setOnItemClickListener { adapter, view, position ->
                TimePickerDialog(
                    requireContext(),
                    { timePicker, hour, minute ->
                        data[position].time = formatTime(hour.toString(), minute.toString())
                        GlobalScope.launch {
                            DataStoreManager.upDataChannelList(data)
                            data = DataStoreManager.getChannelList()
                            LogUtil.logD("当前时间${getCurrentTime()}")
                            requireActivity().runOnUiThread {
                                mAdapter.setList(data)
                            }
                        }
                    }, 0, 0, true
                ).show()
            }
        }
    }

    private fun formatTime(hour: String, minute: String): String {
        val h = if (hour.length == 2) {
            hour
        } else {
            "0${hour}"
        }
        val m = if (minute.length == 2) {
            minute
        } else {
            "0${minute}"
        }
        return "$h:$m"
    }
}