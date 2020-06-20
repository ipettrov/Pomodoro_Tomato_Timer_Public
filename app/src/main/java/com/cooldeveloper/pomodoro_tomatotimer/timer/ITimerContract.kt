package com.cooldeveloper.pomodoro_tomatotimer.timer

interface ITimerContract {

    interface View {

        fun observeViewModel()
        fun sutUpClickListeners()
        fun showPlayFab(state: Boolean)
    }

    interface ViewModel {

        fun handleOnStart()
        fun handleOnPause()
        fun handleOnStop()
        fun handleOnDestroy()

        fun handleOnPlayClicked()
        fun handleOnCancelClicked()
        fun handleOnSettingsClicked()

        fun showErrorState()
        suspend fun createSettingsForFirstTime()
    }

}

sealed class TimerViewEvent {

    object OnCreate : TimerViewEvent()
    object OnStart : TimerViewEvent()
    object OnPause : TimerViewEvent()
    object OnStop : TimerViewEvent()
    object OnDestroy : TimerViewEvent()

    object OnPlayClicked : TimerViewEvent()
    object OnCancelClicked : TimerViewEvent()
    object OnSettingsClicked : TimerViewEvent()
    object OnResume : TimerViewEvent()
}