package com.nandaiqbalh.runningtracker.presentation.ui.onboard.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.FragmentSplashscreenBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.MainActivity
import com.nandaiqbalh.runningtracker.presentation.ui.onboard.OnboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashscreenFragment : Fragment() {

	private var _binding: FragmentSplashscreenBinding? = null
	private val binding get() = _binding!!

	private val viewModel: OnboardViewModel by activityViewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSplashscreenBinding.inflate(layoutInflater, container, false)

		loadingScreen()

		return binding.root
	}

	private fun loadingScreen() {
		Handler(Looper.getMainLooper()).postDelayed(this::isTheFirstTime, 5000)
	}

	private fun isTheFirstTime() {

		lifecycleScope.launchWhenCreated {
			viewModel.getStatusOnboarding().observe(viewLifecycleOwner) {
				if (it == false) {
					findNavController().navigate(R.id.action_splashscreenFragment_to_onboardOneFragment)
				} else {
					startActivity(Intent(requireContext(), MainActivity::class.java))
					activity?.overridePendingTransition(
						android.R.anim.fade_in, android.R.anim.fade_out
					)
					activity?.finish()
				}
			}
		}


	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}