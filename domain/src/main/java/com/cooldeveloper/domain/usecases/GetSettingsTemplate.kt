package com.cooldeveloper.domain.usecases

import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository


/**
 *  When the user opens the application receives previous stored settings with id 0
 *  1. Request settingsTemplate with given id form repository.
 */
class GetSettingsTemplate(private val repository: ISettingsTemplateRepository) :
    UseCaseWithParams<Int, ResultWrapper<Exception, SettingsTemplate>>() {
    override suspend fun buildUseCase(params: Int): ResultWrapper<Exception, SettingsTemplate> {
        return repository.getSettingsTemplateById(params)
    }

}