package com.example.kotlinpractice

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.Constants
import com.example.common.base.BaseActivity
import com.example.common.widget.setupWithNavController
import com.example.home.viewmodel.ArticleViewModel
import com.example.kotlinpractice.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = Constants.PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null
    override fun initData(savedInstanceState: Bundle?) {
        setupBottomNavigationBar()
    }

    private val mViewModel: ArticleViewModel by viewModel()

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.navi_home,R.navigation.navi_project,R.navigation.navi_personal)
        val controller = mBinding?.navView?.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.nav_host_container,
            intent
            )
        controller?.observe(this, Observer {
            navControll ->
            navControll.addOnDestinationChangedListener{
                _,destination,_ ->
                run {
                    val id = destination.id
                    /*if (id == R.id.projectContentFragment) {
                        mBinding?.navView?.visibility = View.GONE
                    } else {
                        mBinding?.navView?.visibility = View.VISIBLE
                    }*/
                }
            }
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }
}