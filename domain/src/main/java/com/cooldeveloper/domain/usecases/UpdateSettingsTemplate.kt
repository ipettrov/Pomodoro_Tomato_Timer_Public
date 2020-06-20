package com.cooldeveloper.domain.usecases

import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository

/**
 * When a user make some changes to the settings template.
 * 1. Make update to the settingsTemplate from the repository.
 */
class UpdateSettingsTemplate(private val repository: ISettingsTemplateRepository) :
    UseCaseWithParams<SettingsTemplate, ResultWrapper<Exception, Unit>>() {
    override suspend fun buildUseCase(params: SettingsTemplate): ResultWrapper<Exception, Unit> {
        return repository.updateSettingsTemplate(params)
    }
}