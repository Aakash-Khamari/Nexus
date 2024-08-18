package com.sovereign.clockify.bottomNavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sovereign.clockify.Dashboard.Dashboard
import com.sovereign.clockify.Log.Log
import com.sovereign.clockify.Map.MapScreen
import com.sovereign.clockify.Profile.Profile


class BottomNavigationItemsAdapter(list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fm,lifecycle) {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size

    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
