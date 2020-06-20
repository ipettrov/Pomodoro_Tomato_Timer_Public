package com.cooldeveloper.pomodoro_tomatotimer.timer

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.common.startWithFadeIn
import com.cooldeveloper.pomodoro_tomatotimer.info.InfoDialogFragment
import com.cooldeveloper.pomodoro_tomatotimer.rating.RateMeDialogFragment
import com.cooldeveloper.pomodoro_tomatotimer.timer.buildlogic.TimerSettingsInjector
import com.cooldeveloper.pomodoro_tomatotimer.update.UpdateDialogFragment
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.toolbar_for_timer_fragment.*

class TimerView : Fragment(), ITimerContract.View {


    companion object {
        @JvmStatic
        fun newInstance() = TimerView()
    }

    private lateinit var viewModel: TimerViewModel

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(
            this,
            TimerSettingsInjector(requireActivity().application).provideUserViewModelFactory(this)
        )
            .get(TimerViewModel::class.java)

        viewModel.handleEvent(TimerViewEvent.OnStart)
    }

    override fun onResume() {
        super.onResume()
        startRateMeTransition()

        viewModel.handleEvent(TimerViewEvent.OnResume)
        observeViewModel()
        sutUpClickListeners()
    }

    private fun startRateMeTransition() {
        (ivRateMe.drawable as AnimationDrawable).startWithFadeIn()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentVersion =
                        FirebaseRemoteConfig.getInstance().getString("current_version")
                    val versionCode = BuildConfig.VERSION_CODE
                    try {
                        if (currentVersion.replace("\"", "").toInt() > versionCode) {
                            showUpdateDialog()
                        }
                    } catch (e: Exception) {

                    }
                }
            }
    }

    private fun showUpdateDialog() {
        UpdateDialogFragment.newInstance().show(parentFragmentManager, "UpdateDialogFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onPause() {
        viewModel.handleEvent(TimerViewEvent.OnPause)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.handleEvent(TimerViewEvent.OnDestroy)
        super.onDestroy()
    }

    override fun observeViewModel() {
        viewModel.secondsText.observe(
            viewLifecycleOwner,
            Observer {
                tvSecondsNumber.text = it
            }
        )

        viewModel.minutesText.observe(
            viewLifecycleOwner,
            Observer {
                tvMinutesNumber.text = it
            }
        )

        viewModel.timerTitle.observe(
            viewLifecycleOwner,
            Observer {
                tvTitle.text = it
            }
        )

        viewModel.resetUi.observe(
            viewLifecycleOwner,
            Observer {
                if (it) resetUi()
            }
        )

        viewModel.showRateMeDialog.observe(
            viewLifecycleOwner,
            Observer {
                if (it) showRateMeDialog()
            }
        )

        viewModel.timerTitleTextStyleBoolean.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    tvTitle.setBackgroundResource(R.drawable.shape_border_rest_color)
                } else {
                    tvTitle.setBackgroundResource(R.drawable.shape_border_primary_color)
                }
            }
        )
    }

    private fun resetUi() {
        tvCancel.visibility = View.GONE
        showPlayFab(true)
    }

    override fun sutUpClickListeners() {
        ivSettings.setOnClickListener { viewModel.handleEvent(TimerViewEvent.OnSettingsClicked) }
        fabStart.setOnClickListener {
            handleStartClick()
            viewModel.handleEvent(TimerViewEvent.OnPlayClicked)
        }

        tvCancel.setOnClickListener {
            showPlayFab(true)
            tvCancel.visibility = View.GONE
            viewModel.handleEvent(TimerViewEvent.OnCancelClicked)
        }

        ivInfo.setOnClickListener {
            InfoDialogFragment.newInstance().show(parentFragmentManager, "InfoDialogFragment")
        }

        ivRateMe.setOnClickListener {
            showRateMeDialog()
        }

    }

    fun showRateMeDialog() {
        RateMeDialogFragment.newInstance().show(parentFragmentManager, "RateMeDialogFragment")
    }

    fun handleStartClick() {
        showPlayFab(false)
        tvCancel.visibility = View.VISIBLE
        viewModel.handleEvent(TimerViewEvent.OnPlayClicked)
    }

    override fun showPlayFab(state: Boolean) {
        if (state) fabStart.show()
        else fabStart.hide()
    }


}
