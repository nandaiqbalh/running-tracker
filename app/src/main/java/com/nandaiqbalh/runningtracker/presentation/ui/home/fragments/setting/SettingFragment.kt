package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nandaiqbalh.runningtracker.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

	private var _binding: FragmentSettingBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSettingBinding.inflate(layoutInflater, container, false)

		return  binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}