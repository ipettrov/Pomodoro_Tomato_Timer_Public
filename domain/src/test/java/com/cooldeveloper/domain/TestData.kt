package com.cooldeveloper.domain

import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.model.ToneSettingsTemplate

fun getSettingsTemplate(
    id: Int = 1,
    workTimeInterval: Long = 5,
    shortRestTimeInterval: Long = 5,
    longRestTimeInterval: Long = 5,
    keepScreenOn: Boolean = false,
    vibrate: Boolean = false,
    sound: Int = 1
) =
    SettingsTemplate(
        id,
        workTimeInterval,
        shortRestTimeInterval,
        longRestTimeInterval,
        keepScreenOn,
        vibrate,
        sound
    )

fun getToneSettingsTemplate(
    ringTone: String = "ringtone1.mp3",
    tickingTone: String = "ticktone1.mp3",
    vibrate: Boolean = true
) = ToneSettingsTemplate(
    ringTone,
    tickingTone,
    vibrate
)
