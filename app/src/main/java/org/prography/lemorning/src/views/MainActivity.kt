package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityMainBinding
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.utils.helpers.setupWithNavController
import org.prography.lemorning.src.viewmodels.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

  private var currentNavController: LiveData<NavController>? = null

  override fun getViewModel(): MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    setupBottomNavigationBar()

    vm.myAlarms.observe(this) {
      if (it.isEmpty()) stopService(Intent(this, AlarmService::class.java))
    }
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    val navGraphIds = listOf(R.navigation.nav_alarm, R.navigation.nav_trending, R.navigation.nav_mypage)

    // Setup the bottom navigation view with a list of navigation graphs
    val controller = binding.bnvMain.setupWithNavController(
      navGraphIds = navGraphIds,
      fragmentManager = supportFragmentManager,
      containerId = R.id.nav_container_main,
      intent = intent
    )
    // Whenever the selected controller changes, setup the action bar.
    controller.observe(this) { navController ->
      //setupActionBarWithNavController(this, navController)
      //NavigationUI.setupWithNavController(binding.bnvMain, navController)
    }
    currentNavController = controller
  }

  override fun onSupportNavigateUp(): Boolean = currentNavController?.value?.navigateUp() == true
}
