package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nandaiqbalh.runningtracker.databinding.FragmentStatisticBinding
import com.nandaiqbalh.runningtracker.presentation.ui.home.viewmodel.StatisticViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : Fragment() {

	private var _binding: FragmentStatisticBinding? = null
	private val binding get() = _binding!!

	private val viewModel: StatisticViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
        _binding = FragmentStatisticBinding.inflate(layoutInflater, container, false)

        return binding.root
	}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}