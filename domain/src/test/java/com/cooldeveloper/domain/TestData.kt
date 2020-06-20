package com.cooldeveloper.domain

import com.cooldeveloper.domain.enums.LongRestTimeInterval
import com.cooldeveloper.domain.enums.ShortRestTimeInterval
import com.cooldeveloper.domain.enums.WorkTimeInterval
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.model.ToneSettingsTemplate

fun getSettingsTemplate(
    id: Int = 1,
    workTimeInterval: WorkTimeInterval = WorkTimeInterval.MINUTES_15,
    shortRestTimeInterval: ShortRestTimeInterval = ShortRestTimeInterval.MINUTES_5,
    longRestTimeInterval: LongRestTimeInterval = LongRestTimeInterval.MINUTES_15,
    toneSettingsTemplate: ToneSettingsTemplate = getToneSettingsTemplate(),
    keepScreenOn: Boolean = false
) =
    SettingsTemplate(
        id,
        workTimeInterval,
        shortRestTimeInterval,
        longRestTimeInterval,
        toneSettingsTemplate,
        keepScreenOn
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
