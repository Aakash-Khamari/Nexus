package com.sovereign.clockify.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sovereign.clockify.R
import com.sovereign.clockify.databinding.FragmentBottomNavBinding
import com.sovereign.clockify.Dashboard.Dashboard
import com.sovereign.clockify.Log.Log
import com.sovereign.clockify.Map.MapScreen
import com.sovereign.clockify.Profile.Profile

class BottomNav : Fragment() {

    private var _binding: FragmentBottomNavBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.homeViewPager
        bottomNavView = binding.bottomNavView

        val fragments = listOf(
            Dashboard(),
            MapScreen(),
            Log(),
            Profile()
        )

        val adapter = BottomNavItemsAdapter(fragments, childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        // Link ViewPager2 with BottomNavigationView
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navDashboard -> viewPager.currentItem = 0
                R.id.navMap -> viewPager.currentItem = 1
                R.id.navAttendance -> viewPager.currentItem = 2
                R.id.navProfile -> viewPager.currentItem = 3
            }
            true
        }

        // Sync ViewPager2 with BottomNavigationView
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavView.menu.getItem(position).isChecked = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
