package com.sixsixsix.asmt.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.sixsixsix.asmt.bean.ChannelType
import com.sixsixsix.asmt.bean.MaoTaiBean
import com.sixsixsix.asmt.bean.MaoTaiItemBean
import com.sixsixsix.asmt.util.LogUtil
import kotlinx.coroutines.flow.*
import java.io.IOException

object DataStoreManager {
    const val channelList = "channel_list"
    var dataStore: DataStore<Preferences>? = null

    suspend fun create(context: Context, storeName: String) {
        dataStore = context.createDataStore(storeName)
        LogUtil.logD("create")
        initMaoTai()
    }

    suspend fun getChannelList(): MutableList<MaoTaiItemBean> {
        val result = mutableListOf<MaoTaiItemBean>()
        getStringDataFlow(channelList, "").first().let {
            if (it.isNullOrEmpty().not()) {
                result.clear()
                result.addAll(Gson().fromJson(it, MaoTaiBean::class.java).channelList)
                LogUtil.logD("getChannelList---${it}---result--${result}")
            }
        }
        return result
    }

    suspend fun upDataChannelList(newData: MutableList<MaoTaiItemBean>) {
        setStringData(channelList, Gson().toJson(MaoTaiBean(channelList = newData)))
    }

    suspend fun initMaoTai() {
        if (getChannelList().size == 0) {
            val list = mutableListOf<MaoTaiItemBean>()
            list.add(MaoTaiItemBean(channelType = ChannelType.SN, time = "09:30"))
            list.add(MaoTaiItemBean(channelType = ChannelType.WY, time = "11:00"))
            list.add(MaoTaiItemBean(channelType = ChannelType.JD, time = "12:00"))
            list.add(MaoTaiItemBean(channelType = ChannelType.TM, time = "20:00"))
            list.add(MaoTaiItemBean(channelType = ChannelType.WY, time = "22:00"))
            setStringData(channelList, Gson().toJson(MaoTaiBean(channelList = list)))
        }
    }

    suspend inline fun setStringData(key: String, value: String) {
        dataStore?.edit { mutablePreferences ->
            mutablePreferences[preferencesKey<String>(key)] = value
        }
    }

    fun getStringDataFlow(key: String, default: String): Flow<String> {
        LogUtil.logD("getStringDataFlow--${key}")
        return dataStore?.data!!.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<String>(key)] ?: default
        }
    }
}