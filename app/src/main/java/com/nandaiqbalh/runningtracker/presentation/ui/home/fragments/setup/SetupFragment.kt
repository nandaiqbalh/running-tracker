package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment(){

	private var _binding: FragmentSetupBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSetupBinding.inflate(layoutInflater, container, false)

		onClickListener()
		return binding.root
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