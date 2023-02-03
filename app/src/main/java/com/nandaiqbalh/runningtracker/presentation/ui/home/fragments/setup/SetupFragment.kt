package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.setup

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentSetupBinding
import com.nandaiqbalh.runningtracker.other.Constants
import com.nandaiqbalh.runningtracker.other.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class SetupFragment : Fragment(), EasyPermissions.PermissionCallbacks {

	private var _binding: FragmentSetupBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSetupBinding.inflate(layoutInflater, container, false)

		onClickListener()

		requestPermission()

		return binding.root
	}


	private fun requestPermission(){

		// if the user already accept permission, just simply return it
		if (TrackingUtility.hasLocationPermission(requireContext())){
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
		TODO("Not yet implemented")
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
		binding.btnNext.setOnClickListener {

			findNavController().navigate(R.id.action_setupFragment_to_runFragment)

		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}