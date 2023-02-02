package com.nandaiqbalh.runningtracker.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var navController: NavController
	private lateinit var bottomNav: BottomNavigationView

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setSupportActionBar(binding.toolbar)

		setContentView(binding.root)

		setUpBottomNavigation()

	}

	private fun setUpBottomNavigation(){
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.fragmentContainerViewHome) as NavHostFragment

		navController = navHostFragment.navController

		bottomNav = findViewById(R.id.bottom_navigation_user)

		val appBarConfiguration = AppBarConfiguration.Builder(
			R.id.runFragment,
			R.id.statisticFragment,
			R.id.settingFragment
		).build()

		bottomNav.itemIconTintList = null

		setupActionBarWithNavController(navController, appBarConfiguration)

		bottomNav.setupWithNavController(navController)
		navController.addOnDestinationChangedListener { _, destination, _ ->
			when (destination.id) {
				R.id.setupFragment, R.id.trackingFragment-> {
					hideBottomNav(true)
				}
				else -> hideBottomNav(false)
			}
		}
	}

	private fun hideBottomNav(hide: Boolean) {
		if (hide) {
			bottomNav.visibility = View.GONE
		} else {
			bottomNav.visibility = View.VISIBLE
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}