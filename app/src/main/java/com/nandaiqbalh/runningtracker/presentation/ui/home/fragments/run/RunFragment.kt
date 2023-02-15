package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.run

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentRunBinding
import com.nandaiqbalh.runningtracker.other.Constants
import com.nandaiqbalh.runningtracker.other.SortType
import com.nandaiqbalh.runningtracker.other.TrackingUtility
import com.nandaiqbalh.runningtracker.presentation.ui.home.adapters.RunAdapter
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class RunFragment : Fragment(), EasyPermissions.PermissionCallbacks{

	private var _binding: FragmentRunBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainViewModel by activityViewModels()

	private lateinit var runAdapter: RunAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentRunBinding.inflate(layoutInflater, container, false)

		onClickListener()

		requestPermission()

		setupRecyclerView()

		shortRun()


		return binding.root
	}

	private fun shortRun(){

		when(viewModel.sortType) {
			SortType.DATE -> binding.spFilter.setSelection(0)
			SortType.RUNNING_TIME -> binding.spFilter.setSelection(1)
			SortType.DISTANCE -> binding.spFilter.setSelection(2)
			SortType.AVG_SPEED -> binding.spFilter.setSelection(3)
			SortType.CALORIES_BURNED -> binding.spFilter.setSelection(4)
		}

		binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(p0: AdapterView<*>?) {}

			override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
				when(pos) {
					0 -> viewModel.sortRuns(SortType.DATE)
					1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
					2 -> viewModel.sortRuns(SortType.DISTANCE)
					3 -> viewModel.sortRuns(SortType.AVG_SPEED)
					4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
				}

			}
		}

		viewModel.runs.observe(viewLifecycleOwner, Observer {
			runAdapter.submitList(it)
		})

	}

	private fun setupRecyclerView() = binding.rvRuns.apply {
		runAdapter = RunAdapter()
		adapter = runAdapter
		layoutManager = LinearLayoutManager(requireContext())
	}

	private fun requestPermission(){

		// if the user already accept permission, just simply return it
		if (TrackingUtility.hasLocationPermissions(requireContext())){
			return
		}

		// request permission (check version code first)
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
			EasyPermissions.requestPermissions(
				this,
				getString(R.string.tv_rationale_permission),
				Constants.REQUEST_CODE_LOCATION_PERMISSION,
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION
			)
		} else{
			EasyPermissions.requestPermissions(
				this,
				getString(R.string.tv_rationale_permission),
				Constants.REQUEST_CODE_LOCATION_PERMISSION,
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.ACCESS_BACKGROUND_LOCATION
			)
		}

	}

	override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
		Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show() // <-- this won't run. the app should be restarted first to make this run

	}

	override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

		// if the user permanently denied the permission, then show dialog again
		// -> because this app really need this permission
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
			AppSettingsDialog.Builder(this).build().show()
		} else {
			requestPermission()
		}
	}

	// redirect the result from android framework to easy permission
	// -> sho that easy permission know the result was
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		// redirect the result
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
	}

	private fun onClickListener(){
		binding.fab.setOnClickListener {
			findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}