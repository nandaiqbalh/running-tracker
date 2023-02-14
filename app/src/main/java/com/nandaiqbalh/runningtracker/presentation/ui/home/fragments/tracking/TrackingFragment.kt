package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.tracking

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentTrackingBinding
import com.nandaiqbalh.runningtracker.other.Constants.ACTION_PAUSE_SERVICE
import com.nandaiqbalh.runningtracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.nandaiqbalh.runningtracker.other.Constants.ACTION_STOP_SERVICE
import com.nandaiqbalh.runningtracker.other.Constants.MAP_ZOOM
import com.nandaiqbalh.runningtracker.other.Constants.POLYLINE_COLOR
import com.nandaiqbalh.runningtracker.other.Constants.POLYLINE_WIDTH
import com.nandaiqbalh.runningtracker.other.TrackingUtility
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.MainViewModel
import com.nandaiqbalh.runningtracker.services.Polyline
import com.nandaiqbalh.runningtracker.services.TrackingService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {

	private var _binding: FragmentTrackingBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainViewModel by activityViewModels()

	private var map: GoogleMap? = null

	private var isTracking = false
	private var pathPoints = mutableListOf<Polyline>()

	private var curTimeInMillis = 0L

	private var menu: Menu? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentTrackingBinding.inflate(layoutInflater, container, false)
		binding.mapView.onCreate(savedInstanceState)

		binding.mapView.getMapAsync {
			map = it
			addAllPolylines()

		}

		binding.btnToggleRun.setOnClickListener {
			sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
			toggleRun()
		}

		subscribeToObservers()

		setHasOptionsMenu(true)

		return binding.root
	}

	private fun subscribeToObservers() {
		TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
			updateTracking(it)
		})

		TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
			pathPoints = it
			addLatestPolyline()
			moveCameraToUser()
		})

		TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
			curTimeInMillis = it
			val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
			binding.tvTimer.text = formattedTime
		})
	}

	private fun toggleRun() {
		if(isTracking) {
			menu?.getItem(0)?.isVisible = true
			sendCommandToService(ACTION_PAUSE_SERVICE)
		} else {
			sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
		}
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.toolbar_tracking_menu, menu)
		this.menu = menu
	}

	override fun onPrepareOptionsMenu(menu: Menu) {
		super.onPrepareOptionsMenu(menu)
		if(curTimeInMillis > 0L) {
			this.menu?.getItem(0)?.isVisible = true
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.miCancelTracking -> {
				showCancelTrackingDialog()
			}
		}
		return super.onOptionsItemSelected(item)
	}

	private fun showCancelTrackingDialog() {
		val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
			.setTitle("Cancel the Run?")
			.setMessage("Are you sure to cancel the current run and delete all its data?")
			.setIcon(R.drawable.ic_delete)
			.setPositiveButton("Yes") { _, _ ->
				stopRun()
			}
			.setNegativeButton("No") { dialogInterface, _ ->
				dialogInterface.cancel()
			}
			.create()
		dialog.show()
		dialog.getWindow()?.setBackgroundDrawableResource(R.color.main_background);

	}

	private fun stopRun() {
		sendCommandToService(ACTION_STOP_SERVICE)
		findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
	}

	private fun updateTracking(isTracking: Boolean) {
		this.isTracking = isTracking
		if(!isTracking) {
			binding.btnToggleRun.setText(R.string.btn_start)
			binding.btnFinishRun.visibility = View.VISIBLE
		} else {
			binding.btnToggleRun.setText(R.string.btn_stop)
			binding.btnFinishRun.visibility = View.GONE
			menu?.getItem(0)?.isVisible = true
		}
	}

	private fun moveCameraToUser() {
		if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
			map?.animateCamera(
				CameraUpdateFactory.newLatLngZoom(
					pathPoints.last().last(),
					MAP_ZOOM
				)
			)
		}
	}

	private fun addAllPolylines() {
		for(polyline in pathPoints) {
			val polylineOptions = PolylineOptions()
				.color(POLYLINE_COLOR)
				.width(POLYLINE_WIDTH)
				.addAll(polyline)
			map?.addPolyline(polylineOptions)
		}
	}

	private fun addLatestPolyline() {
		if(pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
			val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
			val lastLatLng = pathPoints.last().last()
			val polylineOptions = PolylineOptions()
				.color(POLYLINE_COLOR)
				.width(POLYLINE_WIDTH)
				.add(preLastLatLng)
				.add(lastLatLng)
			map?.addPolyline(polylineOptions)
		}
	}

	private fun sendCommandToService(action: String) =
		Intent(requireContext(), TrackingService::class.java).also {
			it.action = action
			requireContext().startService(it)
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