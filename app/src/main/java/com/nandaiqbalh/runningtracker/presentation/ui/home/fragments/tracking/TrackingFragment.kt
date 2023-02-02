package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nandaiqbalh.runningtracker.databinding.FragmentTrackingBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment() {

	private var _binding: FragmentTrackingBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentTrackingBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}