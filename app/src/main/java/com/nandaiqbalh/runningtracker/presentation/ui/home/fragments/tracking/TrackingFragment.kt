package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.GoogleMap
import com.nandaiqbalh.runningtracker.databinding.FragmentTrackingBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {

	private var _binding: FragmentTrackingBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainViewModel by activityViewModels()

	private var map: GoogleMap? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentTrackingBinding.inflate(layoutInflater, container, false)
		binding.mapView.onCreate(savedInstanceState)

		binding.mapView.getMapAsync {
			map = it
		}

		return binding.root
	}

	override fun onResume() {
		super.onResume()
		binding.mapView.onResume()
	}

	override fun onStart() {
		super.onStart()
		binding.mapView.onStart()
	}

	override fun onStop() {
		super.onStop()
		binding.mapView.onStop()
	}

	override fun onPause() {
		super.onPause()
		binding.mapView.onPause()
	}

	override fun onLowMemory() {
		super.onLowMemory()
		binding.mapView.onLowMemory()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		binding.mapView.onSaveInstanceState(outState)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}