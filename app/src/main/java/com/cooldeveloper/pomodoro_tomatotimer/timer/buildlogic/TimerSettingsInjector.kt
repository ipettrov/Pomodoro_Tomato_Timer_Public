package com.cooldeveloper.pomodoro_tomatotimer.timer.buildlogic

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.cooldeveloper.data.settings.AnonymousSettingsDatabase
import com.cooldeveloper.data.settings.RoomLocalAnonymousRepositoryImpl
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository


class TimerSettingsInjector(application: Application) : AndroidViewModel(application) {

    private fun getSettingsRepository(): ISettingsTemplateRepository {
        return RoomLocalAnonymousRepositoryImpl(
            settingsDao = AnonymousSettingsDatabase.getInstance(getApplication()).roomSettingsDao()
        )
    }

    fun provideUserViewModelFactory(view: Fragment): TimerViewModelFactory =
        TimerViewModelFactory(
            view,
            getSettingsRepository()
        )
}