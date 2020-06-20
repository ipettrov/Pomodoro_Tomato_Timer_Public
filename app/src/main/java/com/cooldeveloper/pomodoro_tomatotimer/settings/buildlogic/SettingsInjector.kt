package com.cooldeveloper.pomodoro_tomatotimer.settings.buildlogic

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.cooldeveloper.data.settings.AnonymousSettingsDatabase
import com.cooldeveloper.data.settings.RoomLocalAnonymousRepositoryImpl
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository

class SettingsInjector(application: Application): AndroidViewModel(application) {

    private fun getSettingsRepository(): ISettingsTemplateRepository {
        return RoomLocalAnonymousRepositoryImpl(
            settingsDao = AnonymousSettingsDatabase.getInstance(getApplication()).roomSettingsDao()
        )
    }

    fun provideUserViewModelFactory(view: Fragment): SettingsViewModelFactory =
        SettingsViewModelFactory(
            view,
            getSettingsRepository()
        )
}