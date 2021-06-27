package com.sixsixsix.asmt.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author : jiaBing
 * @date   : 2021/6/27
 * @desc   :
 */
fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(Date())
}
fun getCurrentTimeHMS(): String {
    val formatter = SimpleDateFormat("HH:mm:ss")
    return formatter.format(Date())
}