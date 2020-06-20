package com.cooldeveloper.domain.exception

import java.lang.Exception

sealed class SettingsError: Exception() {

    object SettingsTemplateException: SettingsError()
    object LocalIOException: SettingsError()
}

const val ERROR_UPDATE_FAILED = "Update operation unsuccessful."