package com.cooldeveloper.domain.model

data class WorkingTemplate(
    var settingsTemplate: SettingsTemplate,
    var isStarted: Boolean,
    var isPaused: Boolean,
    var isResting: Boolean,
    var currentTime: Long
)