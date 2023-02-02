package com.nandaiqbalh.runningtracker.data.repositories

import androidx.lifecycle.LiveData
import com.nandaiqbalh.runningtracker.data.local.database.dao.RunDAO
import com.nandaiqbalh.runningtracker.data.local.database.entity.RunEntity
import javax.inject.Inject

interface RunRepository {

	suspend fun insertRun(runEntity: RunEntity)

	suspend fun deleteRun(runEntity: RunEntity)

	fun getAllRunsSortedByDate() : LiveData<List<RunEntity>>

	fun getAllRunsSortedByDistance() : LiveData<List<RunEntity>>

	fun getAllRunsSortedByTimeInMillis() : LiveData<List<RunEntity>>

	fun getAllRunsSortedByAvgSpeed() : LiveData<List<RunEntity>>

	fun getAllRunsSortedByCaloriesBurned() : LiveData<List<RunEntity>>

	fun getTotalAvgSpeed() : LiveData<Float>

	fun getTotalDistance() : LiveData<Int>

	fun getTotalCaloriesBurned() : LiveData<Int>

	fun getTotalTimeInMillis() : LiveData<Long>
}

class RunRepositoryImpl @Inject constructor(
	private val runDAO: RunDAO
) : RunRepository  {

	override suspend fun insertRun(runEntity: RunEntity) {
		return runDAO.insertRun(runEntity)
	}

	override suspend fun deleteRun(runEntity: RunEntity) {
		return runDAO.deleteRun(runEntity)
	}

	override fun getAllRunsSortedByDate(): LiveData<List<RunEntity>> {
		return runDAO.getAllRunsSortedByDate()
	}

	override fun getAllRunsSortedByDistance(): LiveData<List<RunEntity>> {
		return runDAO.getAllRunsSortedByDistance()
	}

	override fun getAllRunsSortedByTimeInMillis(): LiveData<List<RunEntity>> {
		return runDAO.getAllRunsSortedByTimeInMillis()
	}

	override fun getAllRunsSortedByAvgSpeed(): LiveData<List<RunEntity>> {
		return runDAO.getAllRunsSortedByAvgSpeed()
	}

	override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<RunEntity>> {
		return runDAO.getAllRunsSortedByCaloriesBurned()
	}

	override fun getTotalAvgSpeed() : LiveData<Float> {
		return runDAO.getTotalAvgSpeed()
	}

	override fun getTotalDistance() : LiveData<Int> {
		return runDAO.getTotalDistance()
	}

	override fun getTotalCaloriesBurned() : LiveData<Int> {
		return runDAO.getTotalCaloriesBurned()
	}

	override fun getTotalTimeInMillis() : LiveData<Long> {
		return runDAO.getTotalTimeInMillis()
	}
}