package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.run

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nandaiqbalh.runningtracker.databinding.FragmentRunBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment() {

	private var _binding: FragmentRunBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MainViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentRunBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}