package com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.runningtracker.data.local.database.entity.RunEntity
import com.nandaiqbalh.runningtracker.data.repositories.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val runRepository: RunRepository
) : ViewModel (){

	val runsSortedByDate = runRepository.getAllRunsSortedByDate()
	fun insertRun(run: RunEntity) = viewModelScope.launch {
		runRepository.insertRun(run)
	}
}