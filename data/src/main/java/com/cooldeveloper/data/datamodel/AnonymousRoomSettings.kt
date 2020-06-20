package com.cooldeveloper.data.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cooldeveloper.domain.enums.LongRestTimeInterval
import com.cooldeveloper.domain.enums.ShortRestTimeInterval
import com.cooldeveloper.domain.enums.WorkTimeInterval
import com.cooldeveloper.domain.model.ToneSettingsTemplate

@Entity(tableName = "anonymous_settings")
data class AnonymousRoomSettings(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "work_time_interval")
    val workTimeInterval: Long,

    @ColumnInfo(name = "short_rest_time_interval")
    val shortRestTimeInterval: Long,

    @ColumnInfo(name = "long_rest_time_interval")
    val longRestTimeInterval: Long,

    @ColumnInfo(name = "keep_screen_on")
    val keepScreenOn: Boolean,

    @ColumnInfo(name = "vibrate")
    val vibrate: Boolean,

    @ColumnInfo(name = "sound")
    val sound: Int
)