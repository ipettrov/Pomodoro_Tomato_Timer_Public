package com.cooldeveloper.pomodoro_tomatotimer.timer

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.cooldeveloper.domain.ResultWrapper
import com.cooldeveloper.domain.model.SettingsTemplate
import com.cooldeveloper.domain.model.WorkingTemplate
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.common.*
import com.cooldeveloper.pomodoro_tomatotimer.common.CommonObject.isAppRunning
import com.cooldeveloper.pomodoro_tomatotimer.common.CommonObject.isShowNotification
import com.cooldeveloper.pomodoro_tomatotimer.common.CommonObject.stopTimer
import com.cooldeveloper.pomodoro_tomatotimer.rating.RateMeDialogFragment
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TimerViewModel(
    private val repo: ISettingsTemplateRepository,
    private val view: Fragment,
    private val notificationManager: NotificationManager,
    uiContext: CoroutineContext
) : BaseViewModel<TimerViewEvent>(uiContext),
    ITimerContract.ViewModel {

    private var notificationTitle = TEXT_WORK
    private var notificationColor = R.color.secondaryDarkColor

    /**
     * The timer state is private and is divided into small mutable live data that are visible to the view.
     * View can only see and observe the data from the private timer state that we decide.
     */
    private val timerState = MutableLiveData<WorkingTemplate>()

    /**
     * The View can observe and change the timer state(minutes and seconds) by observing this to mutable data objects.
     */
    val minutesText = MutableLiveData<String>()
    val secondsText = MutableLiveData<String>()

    /**
     * The View can observe and change the timer title and it's style by observing this to mutable data objects.
     */
    val timerTitle = MutableLiveData<String>()
    val timerTitleTextStyleBoolean = MutableLiveData<Boolean>()

    val resetUi = MutableLiveData<Boolean>()
    val showRateMeDialog = MutableLiveData<Boolean>()

    /**
     * TimerHandler used as Hellper to handle the time when the timer is started.
     */
    private val timerHandler = TimerHandler(this)

    /**
     * Handle Events: All events that are possible from the view are passed here,
     * and for every event there is a function to handle it.
     */
    override fun handleEvent(event: TimerViewEvent) {
        when (event) {
            is TimerViewEvent.OnCreate -> handleOnCreate()
            is TimerViewEvent.OnResume -> handleOnResume()
            is TimerViewEvent.OnStart -> handleOnStart()
            is TimerViewEvent.OnPause -> handleOnPause()
            is TimerViewEvent.OnStop -> handleOnStop()
            is TimerViewEvent.OnDestroy -> handleOnDestroy()

            is TimerViewEvent.OnPlayClicked -> handleOnPlayClicked()
            is TimerViewEvent.OnCancelClicked -> handleOnCancelClicked()
            is TimerViewEvent.OnSettingsClicked -> handleOnSettingsClicked()
        }
    }

    private fun handleOnResume() {
        increaseRateMeCounter()
        if (!isAppRunning) getTimerState()
    }

    private fun handleOnCreate() {

    }

    /* Handle onStart event */
    override fun handleOnStart() {

    }

    private fun increaseRateMeCounter() {
        val sharedPref =
            view.context?.getSharedPreferences("store_timer_state", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            var rateMeCounter = sharedPref.getInt(SHARED_PREF_RATE_ME_COUNTER, 0)
            rateMeCounter++
            sharedPref.edit().putInt(SHARED_PREF_RATE_ME_COUNTER, rateMeCounter).apply()
            if (rateMeCounter == 4 || rateMeCounter == 7) {
                showRateMeDialog.value = true
            }
        }
    }

    /* Handle onPause event */
    override fun handleOnPause() {

    }

    /* Handle onStop event */
    override fun handleOnStop() {

    }

    /* Handle onDestroy event */
    override fun handleOnDestroy() {
        stopTimer()
        clearSharedPrefs()
    }

    /* Handle the play button click */
    override fun handleOnPlayClicked() {
        resetUi.value = false
        stopTimer = false
        startTimer()
        timerState.value?.isStarted = true
        changeTitleText()
    }

    /* Handle the cancel button click */
    override fun handleOnCancelClicked() {
        notificationTitle = TEXT_WORK
        notificationColor = R.color.secondaryDarkColor
        resetUi.value = true
        view.context?.let {
            NotificationManagerCompat.from(it).cancel(NOTIFICATION_ID)
        }
        isShowNotification = false
        stopTimer()
        resetTimerAndUI()
    }

    /* Handle the settings click event */
    override fun handleOnSettingsClicked() {
        val action = TimerViewDirections.actionTimerFragmentToSettingsFragment()
        view.findNavController().navigate(action)
        handleOnCancelClicked()
    }

    /**
     * Update the current state(title, minutes, seconds) that view is observing
     */
    private fun updateTitle(title: String) {
        timerTitle.value = title
    }

    /* Update mutable minutes data */
    private fun updateMinutes(minutes: String) {
        minutesText.value = minutes
    }

    /* Update mutable seconds data */
    private fun updateSeconds(seconds: String) {
        secondsText.value = seconds
    }

    /* Update what kind of shape the title uses */
    private fun updateTitleTextStyle() {
        val isRestingValue = timerState.value?.isResting
        if (isRestingValue != null) {
            timerTitleTextStyleBoolean.value = isRestingValue
        }
    }

    /* Start the timer here */
    private fun startTimer() {
        isShowNotification = true
        timerHandler.startTimer()
    }

    /* Stop the timer and update the timer state */
    private fun stopTimer() {
        timerHandler.stopTimer()
    }

    /* Update mutable title text data */
    private fun changeTitleText() {
        val currentTimerState = timerState.value

        if (currentTimerState != null) {
            if (currentTimerState.isStarted) {
                when {

                    currentTimerState.isResting -> updateTitle(TEXT_REST_NOW)
                    else -> updateTitle(TEXT_WORK)
                }
            } else {
                updateTitle(TEXT_NOT_STARTED)
            }
        }
    }

    private fun resetTimerAndUI() {
        resetTimeOnTimerState()
        showCurrentTime()

        timerState.value?.isStarted = false
        timerState.value?.isResting = false

        updateTitle(TEXT_NOT_STARTED)
        updateTitleTextStyle()
    }

    /* If the settingsTemplate is not stored create one */
    override suspend fun createSettingsForFirstTime() {
        val settingsTemplate = SettingsTemplate(
            id = SETTINGS_TEMPLATE_ID,
            workTimeInterval = DEFAULT_WORK_TIME * SECONDS_TO_MILLISECONDS,
            shortRestTimeInterval = DEFAULT_RESTING_TIME_SHORT * SECONDS_TO_MILLISECONDS,
            longRestTimeInterval = DEFAULT_WORK_TIME_LONG * SECONDS_TO_MILLISECONDS,
            keepScreenOn = DEFAULT_KEEP_SCREEN_ON,
            vibrate = DEFAULT_VIBRATE,
            sound = DEFAULT_SOUND
        )

        createWorkingTemplate(settingsTemplate)
        repo.updateSettingsTemplate(settingsTemplate)
    }

    /* Reset the timer state */
    private fun resetTimeOnTimerState() {
        val currentTime = timerState.value?.settingsTemplate?.workTimeInterval
        if (currentTime != null) timerState.value?.currentTime = currentTime
    }

    override fun showErrorState() {
        //Not used for now!
    }

    /* Init the timer state when the view is created */
    private fun getTimerState() = launch {
        val result = repo.getSettingsTemplateById(1)

        when (result) {
            is ResultWrapper.Success -> {
                if (result.value == null)
                    createSettingsForFirstTime()
                else
                    checkForSavedInstanceFirst(result.value)
            }
            is ResultWrapper.Error -> createSettingsForFirstTime()
        }
    }

    /* Check if there is saved instance for the workingTemplate before creating one new */
    private fun checkForSavedInstanceFirst(value: SettingsTemplate) {
        createWorkingTemplate(value)
        CommonObject.isAppRunning = true
    }

    /* Clear pref. saved workTemplate state */
    private fun clearSharedPrefs() {
        val sharedPref =
            view.context?.getSharedPreferences("store_timer_state", Context.MODE_PRIVATE)
        sharedPref!!.edit().clear().apply()
    }

    /* Create the working template, when it is not started */
    private fun createWorkingTemplate(settingsTemplate: SettingsTemplate) {
        val workingTemplate = WorkingTemplate(
            settingsTemplate = settingsTemplate,
            isStarted = false,
            isPaused = false,
            isResting = false,
            currentTime = settingsTemplate.workTimeInterval
        )

        timerState.value = workingTemplate
        showCurrentTime()
    }

    /* Update the time for timer state */
    private fun showCurrentTime() {

        val time = timerState.value?.currentTime
        if (time != null) {
            val seconds = (time % 60000) / 1000
            val minutes = time / 60000

            if (minutes == 0.toLong() && seconds == 0.toLong()) {
                val isTimerStateResting = timerState.value?.isResting

                if (isTimerStateResting != null && !isTimerStateResting) {
                    timerState.value?.isResting = true
                    startResting()
                    ringSound()
                    vibratePhone()
                } else {
                    timerState.value?.isResting = false
                    startWorking()
                    ringSound()
                    vibratePhone()
                }

                updateTitleTextStyle()
            }

            if (isShowNotification) {
                view.context?.let {
                    notificationManager.showNotification(
                        String.format("%02d", minutes) + " : " + String.format("%02d", seconds),
                        notificationTitle,
                        notificationColor,
                        it
                    )
                }
            }

            updateMinutes(minutes = String.format("%02d", minutes))
            updateSeconds(seconds = String.format("%02d", seconds))
        }
    }

    private fun ringSound() {
        val state = timerState.value?.settingsTemplate
        if (state != null && state.sound != 0) {
            SoundPlayerHelper.playSound(view.context, state.sound)
        }
    }

    /* Vibrate the phone between work and rest sessions */
    private fun vibratePhone() {
        val vibrator = view.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val settingsState = timerState.value?.settingsTemplate
        if (settingsState != null && settingsState.vibrate) {
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        1000,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                );
            } else {
                vibrator.vibrate(1000);
            }
        }
    }

    /* Update the current state with the working time and update the title
    *  rest -> work
    */
    private fun startWorking() {
        notificationColor = R.color.secondaryDarkColor
        notificationTitle = TEXT_WORK
        val timeForWork = timerState.value?.settingsTemplate?.workTimeInterval
        if (timeForWork != null) {
            timerState.value?.currentTime = timeForWork
            showCurrentTime()
            updateTitle(TEXT_WORK)
        }
    }

    /* Update the current state with the resting time and update the title
    *  work -> rest
    */
    private fun startResting() {
        notificationColor = R.color.restDarkColor
        notificationTitle = TEXT_REST_NOW
        val timeForRest = timerState.value?.settingsTemplate?.shortRestTimeInterval
        if (timeForRest != null) {
            timerState.value?.currentTime = timeForRest
            showCurrentTime()
            updateTitle(TEXT_REST_NOW)
        }
    }

    /* Decrease the time on the timer state */
    fun minusOneSecond() {
        val currentTime = timerState.value?.currentTime
        if (currentTime != null) {
            timerState.value?.currentTime = currentTime - SECOND_1000_MILLISECONDS
            showCurrentTime()
        }
    }

}