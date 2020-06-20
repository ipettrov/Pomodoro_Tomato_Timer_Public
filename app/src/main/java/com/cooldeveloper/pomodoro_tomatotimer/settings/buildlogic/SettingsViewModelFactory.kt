package com.cooldeveloper.pomodoro_tomatotimer.settings.buildlogic

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import com.cooldeveloper.pomodoro_tomatotimer.settings.SettingsViewModel
import com.cooldeveloper.pomodoro_tomatotimer.timer.TimerViewModel
import kotlinx.coroutines.Dispatchers

class SettingsViewModelFactory(
    private val view: Fragment,
    private val settingsRepo: ISettingsTemplateRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SettingsViewModel(settingsRepo, view, Dispatchers.Main) as T
    }

}