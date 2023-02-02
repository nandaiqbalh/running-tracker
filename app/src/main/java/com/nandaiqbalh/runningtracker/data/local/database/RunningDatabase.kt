package com.nandaiqbalh.runningtracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nandaiqbalh.runningtracker.data.local.database.converters.Converters
import com.nandaiqbalh.runningtracker.data.local.database.dao.RunDAO
import com.nandaiqbalh.runningtracker.data.local.database.entity.RunEntity

@Database(
	entities = [RunEntity::class],
	version = 1
)
@TypeConverters(Converters::class)
abstract class RunningDatabase : RoomDatabase() {

	abstract fun getRunDao(): RunDAO
}