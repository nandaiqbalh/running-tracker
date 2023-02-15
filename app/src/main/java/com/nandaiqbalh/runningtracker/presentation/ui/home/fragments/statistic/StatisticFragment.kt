package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.nandaiqbalh.runningtracker.databinding.FragmentStatisticBinding
import com.nandaiqbalh.runningtracker.other.TrackingUtility
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.StatisticViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round

@AndroidEntryPoint
class StatisticFragment : Fragment() {

	private var _binding: FragmentStatisticBinding? = null
	private val binding get() = _binding!!

	private val viewModel: StatisticViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
        _binding = FragmentStatisticBinding.inflate(layoutInflater, container, false)

        return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		subscribeToObservers()
	}

	private fun subscribeToObservers() {
		viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
			it?.let {
				val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
				binding.tvTotalTime.text = totalTimeRun
			}
		})
		viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
			it?.let {
				val km = it / 1000f
				val totalDistance = round(km * 10f) / 10f
				val totalDistanceString = "${totalDistance}km"
				binding.tvTotalDistance.text = totalDistanceString
			}
		})
		viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
			it?.let {
				val avgSpeed = round(it * 10f) / 10f
				val avgSpeedString = "${avgSpeed}km/h"
				binding.tvAverageSpeed.text = avgSpeedString
			}
		})
		viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
			it?.let {
				val totalCalories = "${it}kcal"
				binding.tvTotalCalories.text = totalCalories
			}
		})
	}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}