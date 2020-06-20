package com.cooldeveloper.pomodoro_tomatotimer.settings

interface ISettingsContract {

    interface View {

    }

    interface ViewModel {

        fun handleOnStart()

        fun handleOnWorkTimeWrapperClick()
        fun handleOnRestTimeWrapperClick()
        fun handleOnSoundWrapperClick()

        fun setSoundDescriptionText(text: String)
        fun setRestTimeSelectedState(position: Int)
        fun setWorkTimeSelectedState(position: Int)
        fun setOnSoundItemSelectedState(i: Int)
        fun handleOnRestTime5MinClicked()

        fun handleOnRestTime10MinClicked()
        fun handleOnRestTime15MinClicked()
        fun handleOnWorkTime25MinClicked()

        fun handleOnWorkTime30MinClicked()
        fun handleOnWorkTime35MinClicked()
        fun handleOnWorkTime40MinClicked()
        fun handleOnWorkTime45MinClicked()
        fun handleOnVibrateSwitchChanged()

        fun handleOnMusicNoneClicked()
        fun handleOnMusicDoorBellClicked()
        fun handleOnMusicFrontDeskClicked()
        fun handleOnMusicServiceClicked()

        fun handleOnBackIconClicked()
    }

}

sealed class SettingsViewEvent {
    object OnStart : SettingsViewEvent()

    object OnWorkTimeWrapperClicked : SettingsViewEvent()
    object OnSoundWrapperClicked : SettingsViewEvent()
    object OnRestTimeWrapperClicked : SettingsViewEvent()

    object RestTime5MinClicked : SettingsViewEvent()
    object RestTime10MinClicked : SettingsViewEvent()
    object RestTime15MinClicked : SettingsViewEvent()

    object WorkTime25MinClicked : SettingsViewEvent()
    object WorkTime30MinClicked : SettingsViewEvent()
    object WorkTime35MinClicked : SettingsViewEvent()
    object WorkTime40MinClicked : SettingsViewEvent()
    object WorkTime45MinClicked : SettingsViewEvent()

    object VibrateSwitchChanged : SettingsViewEvent()

    object MusicNoneClicked : SettingsViewEvent()
    object MusicDoorBellClicked : SettingsViewEvent()
    object MusicFrontDeskClicked : SettingsViewEvent()
    object MusicServiceClicked : SettingsViewEvent()

    object OnBackIconClicked : SettingsViewEvent()
}
