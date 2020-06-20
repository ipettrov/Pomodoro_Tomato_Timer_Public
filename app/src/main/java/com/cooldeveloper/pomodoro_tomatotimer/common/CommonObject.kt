package com.cooldeveloper.pomodoro_tomatotimer.common

import android.graphics.drawable.AnimationDrawable

object CommonObject {

    var isAppRunning = false
    var isShowNotification = false
    var stopTimer = false

}

internal fun AnimationDrawable.startWithFadeIn(){
    this.setEnterFadeDuration(1000)
    this.setExitFadeDuration(1000)
    this.start()
}
