package com.nandaiqbalh.runningtracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RunningTrackker : Application(){

	override fun onCreate() {
		super.onCreate()

		Timber.plant(Timber.DebugTree())
	}
}
