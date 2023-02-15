package com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.runningtracker.data.local.database.entity.RunEntity
import com.nandaiqbalh.runningtracker.data.repositories.RunRepository
import com.nandaiqbalh.runningtracker.other.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val runRepository: RunRepository
) : ViewModel (){

	private val runsSortedByDate = runRepository.getAllRunsSortedByDate()
	private val runsSortedByDistance = runRepository.getAllRunsSortedByDistance()
	private val runsSortedByCaloriesBurned = runRepository.getAllRunsSortedByCaloriesBurned()
	private val runsSortedByTimeInMillis = runRepository.getAllRunsSortedByTimeInMillis()
	private val runsSortedByAvgSpeed = runRepository.getAllRunsSortedByAvgSpeed()

	val runs = MediatorLiveData<List<RunEntity>>()

	var sortType = SortType.DATE

	init {
		runs.addSource(runsSortedByDate) { result ->
			if(sortType == SortType.DATE) {
				result?.let { runs.value = it }
			}
		}
		runs.addSource(runsSortedByAvgSpeed) { result ->
			if(sortType == SortType.AVG_SPEED) {
				result?.let { runs.value = it }
			}
		}
		runs.addSource(runsSortedByCaloriesBurned) { result ->
			if(sortType == SortType.CALORIES_BURNED) {
				result?.let { runs.value = it }
			}
		}
		runs.addSource(runsSortedByDistance) { result ->
			if(sortType == SortType.DISTANCE) {
				result?.let { runs.value = it }
			}
		}
		runs.addSource(runsSortedByTimeInMillis) { result ->
			if(sortType == SortType.RUNNING_TIME) {
				result?.let { runs.value = it }
			}
		}
	}

	fun sortRuns(sortType: SortType) = when(sortType) {
		SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
		SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
		SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
		SortType.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
		SortType.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
	}.also {
		this.sortType = sortType
	}
	
	fun insertRun(run: RunEntity) = viewModelScope.launch {
		runRepository.insertRun(run)
	}
}