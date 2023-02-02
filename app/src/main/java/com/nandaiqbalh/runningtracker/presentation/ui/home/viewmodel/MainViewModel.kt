package com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.runningtracker.data.repositories.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val runRepository: RunRepository
) : ViewModel (){

}