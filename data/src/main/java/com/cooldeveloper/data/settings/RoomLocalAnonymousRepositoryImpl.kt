package com.cooldeveloper.data.settings

import com.cooldeveloper.data.toAnonymousRoomSettings
import com.cooldeveloper.data.toSettings
import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.exception.SettingsError
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomLocalAnonymousRepositoryImpl(
    val settingsDao: IAnonymousSettingsDao
) : ISettingsTemplateRepository {

    override suspend fun getSettingsTemplateById(id: Int): ResultWrapper<Exception, SettingsTemplate> =
        withContext(Dispatchers.IO) {
            val settingsTemplate = settingsDao.getSettingsById(id)

            if (settingsTemplate == null) {
                ResultWrapper.build { throw SettingsError.LocalIOException }
            } else {
                ResultWrapper.build { settingsDao.getSettingsById(id).toSettings }
            }
        }

    override suspend fun updateSettingsTemplate(settingsTemplate: SettingsTemplate): ResultWrapper<Exception, Unit> =
        withContext(Dispatchers.IO) {
            val updated =
                settingsDao.insertOrUpdateSettings(settingsTemplate.toAnonymousRoomSettings)

            when (updated) {
                0L -> ResultWrapper.build { throw SettingsError.LocalIOException }
                else -> ResultWrapper.build { Unit }
            }
        }

}