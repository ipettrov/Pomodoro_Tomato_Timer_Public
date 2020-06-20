package com.cooldeveloper.pomodoro_tomatotimer.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cooldeveloper.pomodoro_tomatotimer.R
import kotlinx.android.synthetic.main.activity_main.*

class TimerActivity : AppCompatActivity() {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav = Navigation.findNavController(this, R.id.fragment_nav)
    }

    override fun onDestroy() {
        fragment_nav.onDestroy()
        super.onDestroy()
    }
}
