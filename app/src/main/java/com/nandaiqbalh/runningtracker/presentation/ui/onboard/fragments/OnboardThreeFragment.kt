package com.nandaiqbalh.runningtracker.presentation.ui.onboard.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nandaiqbalh.runningtracker.databinding.FragmentOnboardThreeBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.MainActivity
import com.nandaiqbalh.runningtracker.presentation.ui.onboard.OnboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardThreeFragment : Fragment() {

	private var _binding: FragmentOnboardThreeBinding? = null
	private val binding get() = _binding!!

	private val viewModel: OnboardViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentOnboardThreeBinding.inflate(layoutInflater, container, false)

		onClickListener()

		return binding.root
	}

	private fun onClickListener(){
		binding.btnNext.setOnClickListener {
			startActivity(Intent(requireContext(), MainActivity::class.java))
			activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
			activity?.finish()

			// set status on boarding
			viewModel.setStatusOnboarding(true)
		}

	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}