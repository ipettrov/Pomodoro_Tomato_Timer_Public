package com.cooldeveloper.pomodoro_tomatotimer.common

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.cooldeveloper.pomodoro_tomatotimer.timer.TimerViewModel

class TimerHandler(viewModel:TimerViewModel) {

    private var mainTimerHandler = Handler(Looper.getMainLooper())
    var i = 0
    /* Timer loop here */
    private val updateTextTask = object : Runnable {
        override fun run() {
            i++
            Log.d("timerTag", i.toString())
            if (CommonObject.stopTimer){
                viewModel.handleOnCancelClicked()
                return
            }
            viewModel.minusOneSecond()
            mainTimerHandler.postDelayed(this, SECOND_1000_MILLISECONDS)
        }
    }

    fun startTimer(){
        mainTimerHandler.post(updateTextTask)
    }

    fun stopTimer(){
        mainTimerHandler.removeCallbacks(updateTextTask)
    }
}