package com.example.home.ui

import androidx.core.view.get
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.common.base.BaseFragment
import com.example.home.R
import com.example.home.adapter.HomePagerAdapter
import com.example.home.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment :BaseFragment<FragmentHomeBinding>() {
    private lateinit var homePagerAdapter: HomePagerAdapter
    private val tabNames = listOf("每日一问","首页","广场")
    private val fragments = listOf(DailyQuestionFragment(),ArticleFragment(),SquareFragment())

    override fun initData() {
        initTab(mBinding?.tab)
        homePagerAdapter = HomePagerAdapter(fragments,fragmentManager = parentFragmentManager,
            behavior = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        mBinding?.vp?.adapter = homePagerAdapter
        mBinding?.vp?.apply {
            adapter = homePagerAdapter
            addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                    TODO("Not yet implemented")
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    TODO("Not yet implemented")
                }

                override fun onPageSelected(position: Int) {
                    mBinding!!.tab.get(position).isSelected = true
                }
            })
        }
        mBinding?.tab?.apply {
            setupWithViewPager(mBinding?.vp)
            get(1).isSelected = true
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var position = tab?.position
                    mBinding!!.vp.currentItem = position!!
                }
            })
        }
    }

    private fun initTab(tab: TabLayout?) {
        for (i in tabNames.indices) {
            tab?.addTab(tab.newTab())
        }
        for (i in tabNames.indices) {
            tab?.getTabAt(i)?.setText(tabNames[i])
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }
}