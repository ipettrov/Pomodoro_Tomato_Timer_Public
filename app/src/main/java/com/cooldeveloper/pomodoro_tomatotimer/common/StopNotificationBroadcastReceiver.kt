package com.cooldeveloper.pomodoro_tomatotimer.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cooldeveloper.pomodoro_tomatotimer.common.CommonObject.stopTimer


class StopNotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            stopTimer = true
        }
    }
}