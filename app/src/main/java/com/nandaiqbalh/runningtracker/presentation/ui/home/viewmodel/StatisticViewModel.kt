package com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.runningtracker.data.repositories.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
	private val runRepository: RunRepository
) : ViewModel (){
	val totalTimeRun = runRepository.getTotalTimeInMillis()
	val totalDistance = runRepository.getTotalDistance()
	val totalCaloriesBurned = runRepository.getTotalCaloriesBurned()
	val totalAvgSpeed = runRepository.getTotalAvgSpeed()

	val runsSortedByDate = runRepository.getAllRunsSortedByDate()
}