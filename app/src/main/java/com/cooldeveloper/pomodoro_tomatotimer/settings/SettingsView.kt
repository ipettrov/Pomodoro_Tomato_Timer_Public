package com.cooldeveloper.pomodoro_tomatotimer.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.settings.buildlogic.SettingsInjector
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.toolbar_for_settings_fragment.*


class SettingsFragment : Fragment(), ISettingsContract.View {

    val event = MutableLiveData<SettingsViewEvent>()
    private lateinit var viewModel: SettingsViewModel

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(
            this,
            SettingsInjector(requireActivity().application).provideUserViewModelFactory(this)
        ).get(SettingsViewModel::class.java)

        viewModel.handleEvent(SettingsViewEvent.OnStart)
        setObservers()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        switchVibrate.setOnClickListener {
            viewModel.handleEvent(SettingsViewEvent.VibrateSwitchChanged)
        }

        musicWrapper.setOnClickListener {

        }

        workTimeWrapper.setOnClickListener {
            viewModel.handleEvent(SettingsViewEvent.OnWorkTimeWrapperClicked)
        }

        restTimeWrapper.setOnClickListener {
            viewModel.handleEvent(SettingsViewEvent.OnRestTimeWrapperClicked)
        }

        ivBack.setOnClickListener {
            viewModel.handleEvent(SettingsViewEvent.OnBackIconClicked)
        }

        musicWrapper.setOnClickListener {
            viewModel.handleEvent(SettingsViewEvent.OnSoundWrapperClicked)
        }

    }

    private fun showRestTimeChooser(position: Int) {
        val listItems = arrayOf("5 minutes", "10 minutes", "15 minutes")
        val mBuilder = AlertDialog.Builder(this.context)
        mBuilder.setTitle("Your resting time")
        mBuilder.setSingleChoiceItems(listItems, position) { dialogInterface, i ->
            when (i) {
                0 -> viewModel.handleEvent(SettingsViewEvent.RestTime5MinClicked)
                1 -> viewModel.handleEvent(SettingsViewEvent.RestTime10MinClicked)
                2 -> viewModel.handleEvent(SettingsViewEvent.RestTime15MinClicked)
            }
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun showSoundChooser(position: Int) {
        val listItems = arrayOf("None", "Doorbell", "Front desk bell", "Service bell")
        val mBuilder = AlertDialog.Builder(this.context)
        mBuilder.setTitle("Sound between work/rest")
        mBuilder.setSingleChoiceItems(listItems, position) { dialogInterface, i ->
            when (i) {
                0 -> viewModel.handleEvent(SettingsViewEvent.MusicNoneClicked)
                1 -> viewModel.handleEvent(SettingsViewEvent.MusicDoorBellClicked)
                2 -> viewModel.handleEvent(SettingsViewEvent.MusicFrontDeskClicked)
                3 -> viewModel.handleEvent(SettingsViewEvent.MusicServiceClicked)
            }
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setObservers() {
        viewModel.vibrateStateBoolean.observe(
            viewLifecycleOwner,
            Observer {
                switchVibrate.isChecked = it
            }
        )

        viewModel.restTimeDescriptionState.observe(
            viewLifecycleOwner,
            Observer {
                tvRestTimeDescription.text = it
            }
        )

        viewModel.workTimeDescriptionState.observe(
            viewLifecycleOwner,
            Observer {
                tvWorkTimeDescription.text = it
            }
        )

        viewModel.restTimeSelectedState.observe(
            viewLifecycleOwner,
            Observer {
                showRestTimeChooser(it)
            }
        )

        viewModel.workTimeSelectedState.observe(
            viewLifecycleOwner,
            Observer {
                showWorkTimeChooser(it)
            }
        )

        viewModel.soundSelectedState.observe(
            viewLifecycleOwner,
            Observer {
                showSoundChooser(it)
            }
        )

        viewModel.soundDescriptionState.observe(
            viewLifecycleOwner,
            Observer {
                tvMusicDescription.text = it
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private fun showWorkTimeChooser(position: Int) {
        val listItems =
            arrayOf("25 minutes", "30 minutes", "35 minutes", "40 minutes", "45 minutes")
        val mBuilder = AlertDialog.Builder(this.context)
        mBuilder.setTitle("Your working time")
        mBuilder.setSingleChoiceItems(listItems, position) { dialogInterface, i ->
            when (i) {
                0 -> viewModel.handleEvent(SettingsViewEvent.WorkTime25MinClicked)
                1 -> viewModel.handleEvent(SettingsViewEvent.WorkTime30MinClicked)
                2 -> viewModel.handleEvent(SettingsViewEvent.WorkTime35MinClicked)
                3 -> viewModel.handleEvent(SettingsViewEvent.WorkTime40MinClicked)
                4 -> viewModel.handleEvent(SettingsViewEvent.WorkTime45MinClicked)
            }
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment().apply {}
    }
}
