package com.cooldeveloper.pomodoro_tomatotimer.common

import com.cooldeveloper.pomodoro_tomatotimer.R

/**
 * This constants are used to calculate minutes to milliseconds,
 * also to subtract one second when the timer is started.
 */
internal const val SECONDS_TO_MILLISECONDS = 60000L
internal const val SECOND_1000_MILLISECONDS = 1000L

/**
 * This constants are used for the sounds offered in the settings fragment.
 */
internal const val SOUND_NONE = 0
internal const val SOUND_DOORBELL = R.raw.doorbell
internal const val SOUND_FRONT_DESK_BELL = R.raw.front_desk_bell
internal const val SOUND_SERVICE_BELL = R.raw.service_bell

internal const val TEXT_NONE = "None"
internal const val TEXT_DOORBELL = "Doorbell"
internal const val TEXT_FRONT_DESK_BELL = "Front desk bell"
internal const val TEXT_SERVICE_BELL = "Service bell"

/**
 * This constants are used as time offered as work and rest time.
 */
internal const val MINUTES_5 = 5L
internal const val MINUTES_10 = 10L
internal const val MINUTES_15 = 15L
internal const val MINUTES_25 = 25L
internal const val MINUTES_30 = 30L
internal const val MINUTES_35 = 35L
internal const val MINUTES_40 = 40L
internal const val MINUTES_45 = 45L

/**
 * This constants are used to create the first settings template as default values.
 */
internal const val DEFAULT_WORK_TIME = 25
internal const val DEFAULT_RESTING_TIME_SHORT = 5
internal const val DEFAULT_WORK_TIME_LONG = 15
internal const val DEFAULT_KEEP_SCREEN_ON = true
internal const val DEFAULT_VIBRATE = true
internal const val DEFAULT_SOUND = R.raw.doorbell
internal const val SETTINGS_TEMPLATE_ID = 1

/**
 * This constants are displayed as timer state in timer fragment.
 * Future note: in case that localization will be needed this constant should be moved to R.string.*
 */
internal const val TEXT_WORK = "Work"
internal const val TEXT_REST_NOW = "Rest now !"
internal const val TEXT_NOT_STARTED = "Not started"
internal const val TEXT_MINUTES = "minutes"


internal const val CHANNEL_ID = "Timer"
internal const val NOTIFICATION_ID = 1559
internal const val CHANNEL_DESCRIPTION = "Pomodoro Interval Timer"
internal const val CHANNEL_DISMISS_TEXT = "CANCEL"

internal const val STOP_PENDING_INTENT_REQUEST_CODE = 999
internal const val STOP_INTENT_ACTION = "DISMISS"

internal const val SHARED_PREF_RATE_ME_COUNTER = "RATE_ME_COUNTER"

internal const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.cooldeveloper.pomodoro_tomatotimer"

