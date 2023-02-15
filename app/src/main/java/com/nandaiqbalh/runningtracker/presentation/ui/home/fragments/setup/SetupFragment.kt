package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.setup

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentSetupBinding
import com.nandaiqbalh.runningtracker.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.nandaiqbalh.runningtracker.other.Constants.KEY_NAME
import com.nandaiqbalh.runningtracker.other.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(){

	private var _binding: FragmentSetupBinding? = null
	private val binding get() = _binding!!

	@Inject
	lateinit var sharedPref: SharedPreferences

	@set:Inject
	var isFirstAppOpen = true
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSetupBinding.inflate(layoutInflater, container, false)

		if(!isFirstAppOpen) {
			val navOptions = NavOptions.Builder()
				.setPopUpTo(R.id.setupFragment, true)
				.build()
			findNavController().navigate(
				R.id.action_setupFragment_to_runFragment,
				savedInstanceState,
				navOptions
			)
		}

		onClickListener()
		return binding.root
	}
	private fun onClickListener(){
		binding.btnNext.setOnClickListener {

			val success = writePersonalDataToSharedPref()
			if(success) {
				findNavController().navigate(R.id.action_setupFragment_to_runFragment)
			} else {
				Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_SHORT).show()
			}
		}
	}

	private fun writePersonalDataToSharedPref(): Boolean {
		val name = binding.etName.text.toString()
		val weight = binding.etWeight.text.toString()
		if(name.isEmpty() || weight.isEmpty()) {
			return false
		}
		sharedPref.edit()
			.putString(KEY_NAME, name)
			.putFloat(KEY_WEIGHT, weight.toFloat())
			.putBoolean(KEY_FIRST_TIME_TOGGLE, false)
			.apply()
		val toolbarText = "Let's go, $name!"
		requireActivity().title = toolbarText
		return true
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}