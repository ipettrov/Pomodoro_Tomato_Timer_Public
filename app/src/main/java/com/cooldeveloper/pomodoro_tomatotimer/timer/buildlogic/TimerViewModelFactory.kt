package com.cooldeveloper.pomodoro_tomatotimer.timer.buildlogic

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import com.cooldeveloper.pomodoro_tomatotimer.common.NotificationManager
import com.cooldeveloper.pomodoro_tomatotimer.timer.TimerViewModel
import kotlinx.coroutines.Dispatchers

class TimerViewModelFactory(
    private val view: Fragment,
    private val settingsRepo: ISettingsTemplateRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return TimerViewModel(settingsRepo, view, NotificationManager(), Dispatchers.Main) as T
    }

}