package com.nandaiqbalh.runningtracker.di

import com.nandaiqbalh.runningtracker.data.repositories.RunRepository
import com.nandaiqbalh.runningtracker.data.repositories.RunRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindsRunRepository(runRepositoryImpl: RunRepositoryImpl): RunRepository
}