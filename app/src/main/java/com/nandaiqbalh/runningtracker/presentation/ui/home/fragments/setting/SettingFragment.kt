package com.nandaiqbalh.runningtracker.presentation.ui.home.fragments.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.nandaiqbalh.runningtracker.databinding.FragmentSettingBinding
import com.nandaiqbalh.runningtracker.other.Constants.KEY_NAME
import com.nandaiqbalh.runningtracker.other.Constants.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {

	private var _binding: FragmentSettingBinding? = null
	private val binding get() = _binding!!

	@Inject
	lateinit var sharedPreferences: SharedPreferences
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSettingBinding.inflate(layoutInflater, container, false)

		return  binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		loadFieldsFromSharedPref()
		binding.btnApplyChanges.setOnClickListener {
			val success = applyChangesToSharedPref()
			if(success) {
				Snackbar.make(view, "Saved changes", Snackbar.LENGTH_LONG).show()
			} else {
				Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_LONG).show()
			}
		}
	}

	private fun loadFieldsFromSharedPref() {
		val name = sharedPreferences.getString(KEY_NAME, "")
		val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
		binding.etName.setText(name)
		binding.etWeight.setText(weight.toString())
	}

	private fun applyChangesToSharedPref(): Boolean {
		val nameText =binding.etName.text.toString()
		val weightText = binding.etWeight.text.toString()
		if(nameText.isEmpty() || weightText.isEmpty()) {
			return false
		}
		sharedPreferences.edit()
			.putString(KEY_NAME, nameText)
			.putFloat(KEY_WEIGHT, weightText.toFloat())
			.apply()
		val toolbarText = "Let's go $nameText"
		requireActivity().title = toolbarText
		return true
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}