package com.cooldeveloper.pomodoro_tomatotimer.rating

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cooldeveloper.pomodoro_tomatotimer.R
import com.cooldeveloper.pomodoro_tomatotimer.common.PLAY_STORE_URL
import kotlinx.android.synthetic.main.fragment_rate_me_dialog.*


class RateMeDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_me_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRateMe.setOnClickListener {
            val url = PLAY_STORE_URL
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RateMeDialogFragment().apply { }
    }
}
