package com.cooldeveloper.pomodoro_tomatotimer.settings

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.common.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SettingsViewModel(
    private val repo: ISettingsTemplateRepository,
    private val view: Fragment,
    uiContext: CoroutineContext
) : BaseViewModel<SettingsViewEvent>(uiContext),
    ISettingsContract.ViewModel {

    private val settingsState = MutableLiveData<SettingsTemplate>()

    val workTimeSelectedState = MutableLiveData<Int>()
    val workTimeDescriptionState = MutableLiveData<String>()

    val restTimeDescriptionState = MutableLiveData<String>()
    val restTimeSelectedState = MutableLiveData<Int>()

    val vibrateStateBoolean = MutableLiveData<Boolean>()

    val soundSelectedState = MutableLiveData<Int>()
    val soundDescriptionState = MutableLiveData<String>()

    override fun handleEvent(event: SettingsViewEvent) {
        when (event) {
            is SettingsViewEvent.OnStart                    ->  handleOnStart()

            is SettingsViewEvent.OnWorkTimeWrapperClicked   ->  handleOnWorkTimeWrapperClick()
            is SettingsViewEvent.OnRestTimeWrapperClicked   ->  handleOnRestTimeWrapperClick()
            is SettingsViewEvent.OnSoundWrapperClicked      ->  handleOnSoundWrapperClick()

            is SettingsViewEvent.OnBackIconClicked          ->  handleOnBackIconClicked()

            is SettingsViewEvent.RestTime5MinClicked        ->  handleOnRestTime5MinClicked()
            is SettingsViewEvent.RestTime10MinClicked       ->  handleOnRestTime10MinClicked()
            is SettingsViewEvent.RestTime15MinClicked       ->  handleOnRestTime15MinClicked()

            is SettingsViewEvent.WorkTime25MinClicked       ->  handleOnWorkTime25MinClicked()
            is SettingsViewEvent.WorkTime30MinClicked       ->  handleOnWorkTime30MinClicked()
            is SettingsViewEvent.WorkTime35MinClicked       ->  handleOnWorkTime35MinClicked()
            is SettingsViewEvent.WorkTime40MinClicked       ->  handleOnWorkTime40MinClicked()
            is SettingsViewEvent.WorkTime45MinClicked       ->  handleOnWorkTime45MinClicked()

            is SettingsViewEvent.VibrateSwitchChanged       ->  handleOnVibrateSwitchChanged()

            is SettingsViewEvent.MusicNoneClicked           ->  handleOnMusicNoneClicked()
            is SettingsViewEvent.MusicDoorBellClicked       ->  handleOnMusicDoorBellClicked()
            is SettingsViewEvent.MusicFrontDeskClicked      ->  handleOnMusicFrontDeskClicked()
            is SettingsViewEvent.MusicServiceClicked        ->  handleOnMusicServiceClicked()
        }
    }

    override fun handleOnStart() {
        getSettingsState()
    }

    override fun handleOnWorkTimeWrapperClick() {
        val state = settingsState.value
        if (state != null) {
            when (state.workTimeInterval / SECONDS_TO_MILLISECONDS) {
                MINUTES_25 -> setWorkTimeSelectedState(0)
                MINUTES_30 -> setWorkTimeSelectedState(1)
                MINUTES_35 -> setWorkTimeSelectedState(2)
                MINUTES_40 -> setWorkTimeSelectedState(3)
                MINUTES_45 -> setWorkTimeSelectedState(4)
                else -> setWorkTimeSelectedState(-1)
            }
        }
    }

    override fun handleOnRestTimeWrapperClick() {
        val state = settingsState.value

        if (state != null) {
            when (state.shortRestTimeInterval / SECONDS_TO_MILLISECONDS) {
                MINUTES_5 -> setRestTimeSelectedState(0)
                MINUTES_10 -> setRestTimeSelectedState(1)
                MINUTES_15 -> setRestTimeSelectedState(2)
                else -> setRestTimeSelectedState(-1)
            }
        }
    }

    override fun handleOnSoundWrapperClick() {
        val state = settingsState.value

        if (state != null) {
            when (state.sound) {
                R.raw.doorbell -> setOnSoundItemSelectedState(1)
                R.raw.front_desk_bell -> setOnSoundItemSelectedState(2)
                R.raw.service_bell -> setOnSoundItemSelectedState(3)
                else -> setOnSoundItemSelectedState(0)
            }
        }
    }

    override fun handleOnBackIconClicked() {
        view.activity?.onBackPressed()
    }

    override fun setSoundDescriptionText(text: String) {
        soundDescriptionState.value = text
    }

    override fun setRestTimeSelectedState(position: Int) {
        restTimeSelectedState.value = position
    }

    override fun setWorkTimeSelectedState(position: Int) {
        workTimeSelectedState.value = position
    }

    override fun setOnSoundItemSelectedState(i: Int) {
        soundSelectedState.value = i
    }

    override fun handleOnRestTime5MinClicked() {
        setNewRestTimeDescriptionText(MINUTES_5)
        changeTheCurrentRestState(MINUTES_5)
    }

    override fun handleOnRestTime10MinClicked() {
        setNewRestTimeDescriptionText(MINUTES_10)
        changeTheCurrentRestState(MINUTES_10)
    }

    override fun handleOnRestTime15MinClicked() {
        setNewRestTimeDescriptionText(MINUTES_15)
        changeTheCurrentRestState(MINUTES_15)
    }

    override fun handleOnWorkTime25MinClicked() {
        setNewWorkTimeDescriptionText(MINUTES_25)
        changeTheCurrentWorkState(MINUTES_25)
    }

    override fun handleOnWorkTime30MinClicked() {
        setNewWorkTimeDescriptionText(MINUTES_30)
        changeTheCurrentWorkState(MINUTES_30)
    }

    override fun handleOnWorkTime35MinClicked() {
        setNewWorkTimeDescriptionText(MINUTES_35)
        changeTheCurrentWorkState(MINUTES_35)
    }

    override fun handleOnWorkTime40MinClicked() {
        setNewWorkTimeDescriptionText(MINUTES_40)
        changeTheCurrentWorkState(MINUTES_40)
    }

    override fun handleOnWorkTime45MinClicked() {
        setNewWorkTimeDescriptionText(MINUTES_45)
        changeTheCurrentWorkState(MINUTES_45)
    }

    override fun handleOnVibrateSwitchChanged() {
        val currentVibrateState = vibrateStateBoolean.value
        if (currentVibrateState != null) updateVibrationSwitch(!currentVibrateState)

        val state = settingsState.value
        if (state != null && currentVibrateState != null) {
            state.vibrate = !currentVibrateState
            settingsState.value = state
        }
        saveSettingsTemplateToDatabase()
    }

    override fun handleOnMusicNoneClicked() {
        setSoundDescriptionText(TEXT_NONE)
        changeCurrentSoundState(SOUND_NONE)
    }

    override fun handleOnMusicServiceClicked() {
        setSoundDescriptionText(TEXT_SERVICE_BELL)
        SoundPlayerHelper.playSound(view.context, SOUND_SERVICE_BELL)
        changeCurrentSoundState(SOUND_SERVICE_BELL)
    }

    override fun handleOnMusicFrontDeskClicked() {
        setSoundDescriptionText(TEXT_FRONT_DESK_BELL)
        SoundPlayerHelper.playSound(view.context, SOUND_FRONT_DESK_BELL)
        changeCurrentSoundState(SOUND_FRONT_DESK_BELL)
    }

    override fun handleOnMusicDoorBellClicked() {
        setSoundDescriptionText(TEXT_DOORBELL)
        SoundPlayerHelper.playSound(view.context, SOUND_DOORBELL)
        changeCurrentSoundState(SOUND_DOORBELL)
    }


    private fun setNewWorkTimeDescriptionText(time: Long) {
        workTimeDescriptionState.value = "$time $TEXT_MINUTES"
    }

    private fun setNewRestTimeDescriptionText(time: Long) {
        restTimeDescriptionState.value = "$time $TEXT_MINUTES"
    }

    private fun changeTheCurrentWorkState(time: Long) {
        val currentSettingsState = settingsState.value
        if (currentSettingsState != null) {
            currentSettingsState.workTimeInterval = time * SECONDS_TO_MILLISECONDS
            saveSettingsTemplateToDatabase()
        }

        CommonObject.isAppRunning = false
    }

    private fun changeTheCurrentRestState(time: Long) {
        val currentSettingsState = settingsState.value
        if (currentSettingsState != null) {
            currentSettingsState.shortRestTimeInterval = time * SECONDS_TO_MILLISECONDS
            saveSettingsTemplateToDatabase()
        }

        CommonObject.isAppRunning = false
    }

    private fun changeCurrentSoundState(sound: Int) {
        val currentSettingsState = settingsState.value
        if (currentSettingsState != null) {
            currentSettingsState.sound = sound
            saveSettingsTemplateToDatabase()
        }

        CommonObject.isAppRunning = false
    }

    private fun getSettingsState() = launch {
        val result = repo.getSettingsTemplateById(1)

        when (result) {
            is ResultWrapper.Success -> {
                updateUIWithNewSettingsState(result.value)
            }
            is ResultWrapper.Error -> {
                showErrorState()
            }
        }
    }

    private fun updateUIWithNewSettingsState(value: SettingsTemplate) {
        settingsState.value = value
        updateVibrationSwitch(value.vibrate)
        updateSoundDescription(value.sound)
        setNewRestTimeDescriptionText(value.shortRestTimeInterval / 60000)
        setNewWorkTimeDescriptionText(value.workTimeInterval / 60000)
    }

    private fun updateSoundDescription(sound: Int) {
        when (sound) {
            R.raw.doorbell -> setSoundDescriptionText(TEXT_DOORBELL)
            R.raw.front_desk_bell -> setSoundDescriptionText(TEXT_FRONT_DESK_BELL)
            R.raw.service_bell -> setSoundDescriptionText(TEXT_SERVICE_BELL)
            else -> setSoundDescriptionText(TEXT_NONE)
        }
    }

    private fun updateVibrationSwitch(vibrate: Boolean) {
        vibrateStateBoolean.value = vibrate
    }

    private fun saveSettingsTemplateToDatabase() = launch {
        val state = settingsState.value
        if (state != null) {
            val result = repo.updateSettingsTemplate(state)
            when (result) {
                is ResultWrapper.Success -> {
                    //not used for now
                }
                is ResultWrapper.Error -> {
                    // not used for now
                }
            }
        }
    }

    private fun showErrorState() {
        // Not used for now
    }
}