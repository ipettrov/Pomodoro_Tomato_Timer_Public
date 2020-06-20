package com.cooldeveloper.domain.repository

import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.model.SettingsTemplate

interface ISettingsTemplateRepository {
    suspend fun getSettingsTemplateById(id: Int): ResultWrapper<Exception, SettingsTemplate>
    suspend fun updateSettingsTemplate(settingsTemplate: SettingsTemplate): ResultWrapper<Exception, Unit>
}