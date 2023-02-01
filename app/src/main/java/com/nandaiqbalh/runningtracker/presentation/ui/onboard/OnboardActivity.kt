package com.nandaiqbalh.runningtracker.presentation.ui.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nandaiqbalh.runningtracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar?.hide()
		setContentView(R.layout.activity_onboard)
	}
}