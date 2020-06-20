package com.cooldeveloper.data

import com.cooldeveloper.data.datamodel.AnonymousRoomSettings
import com.cooldeveloper.domain.model.SettingsTemplate

internal val SettingsTemplate.toAnonymousRoomSettings: AnonymousRoomSettings
    get() = AnonymousRoomSettings(
        this.id,
        this.workTimeInterval,
        this.shortRestTimeInterval,
        this.longRestTimeInterval,
        this.keepScreenOn,
        this.vibrate,
        this.sound
    )

internal val AnonymousRoomSettings.toSettings: SettingsTemplate
    get() = SettingsTemplate(
        this.id,
        this.workTimeInterval,
        this.shortRestTimeInterval,
        this.longRestTimeInterval,
        this.keepScreenOn,
        this.vibrate,
        this.sound
    )