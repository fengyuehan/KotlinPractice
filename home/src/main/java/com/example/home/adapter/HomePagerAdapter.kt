package com.example.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(private var fragments: List<Fragment>, fragmentManager: FragmentManager, behavior:Int) :FragmentPagerAdapter(fragmentManager,behavior) {
    override fun getItem(position: Int): Fragment {
       return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}