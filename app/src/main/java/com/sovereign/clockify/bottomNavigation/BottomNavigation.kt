package com.sovereign.clockify.bottomNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sovereign.clockify.Dashboard.Dashboard
import com.sovereign.clockify.Log.Log
import com.sovereign.clockify.Map.MapScreen
import com.sovereign.clockify.Profile.Profile
import com.sovereign.clockify.R
import com.sovereign.clockify.databinding.FragmentBottomNavigationBinding

class BottomNavigation : Fragment() {

    private lateinit var bottomNavigationItemsAdapter: BottomNavigationItemsAdapter

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)

        setOnboardingItems()

        return binding.root
    }

    private fun setOnboardingItems() {
        // Set up the ViewPager2 with the adapter
        bottomNavigationItemsAdapter = BottomNavigationItemsAdapter(
            listOf(
                Dashboard(),
                MapScreen(),
                Log(),
                Profile()
            ),
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = binding.homeViewPager
        viewPager.adapter = bottomNavigationItemsAdapter

        // Disable swiping
        viewPager.isUserInputEnabled = false

        // Synchronize ViewPager2 with BottomNavigationView
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavView.menu.getItem(position).isChecked = true
            }
        })

        // Synchronize BottomNavigationView with ViewPager2
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navDashboard -> viewPager.currentItem = 0
                R.id.navMap -> viewPager.currentItem = 1
                R.id.navAttendance -> viewPager.currentItem = 2
                R.id.navProfile -> viewPager.currentItem = 3
                else -> false
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}