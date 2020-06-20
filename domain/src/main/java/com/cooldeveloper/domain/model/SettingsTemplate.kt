package com.cooldeveloper.domain.model

import com.cooldeveloper.domain.enums.LongRestTimeInterval
import com.cooldeveloper.domain.enums.ShortRestTimeInterval
import com.cooldeveloper.domain.enums.WorkTimeInterval

data class SettingsTemplate(
    val id:Int,
    var workTimeInterval: Long,
    var shortRestTimeInterval: Long,
    var longRestTimeInterval: Long,
    var keepScreenOn: Boolean,
    var vibrate: Boolean,
    var sound: Int
)

