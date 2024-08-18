package com.sovereign.clockify.bottomNavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sovereign.clockify.Dashboard.Dashboard
import com.sovereign.clockify.Log.Log
import com.sovereign.clockify.Map.MapScreen
import com.sovereign.clockify.Profile.Profile


class BottomNavigationItemsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Dashboard()
            1 -> MapScreen()
            2 -> Log()
            3 -> Profile()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
