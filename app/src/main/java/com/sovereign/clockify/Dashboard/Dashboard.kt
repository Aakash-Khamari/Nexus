package com.sovereign.clockify.Dashboard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sovereign.clockify.R
import com.sovereign.clockify.databinding.FragmentDashboardBinding
import kotlinx.coroutines.NonCancellable.start
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Dashboard : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var isOnBreak = false
    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize animations here
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        // Set Date Function
        setDate()
        // Set Date Function
        setTime()


        binding.clockInBtn.setOnClickListener(){
            handleClockInAction()
        }

        binding.TakeABreakBtn.setOnClickListener(){
            handleBreakAction()
        }

        binding.clockOutBtn.setOnClickListener(){
            handleClockOutAction()
        }

        val viewPager = activity?.findViewById<ViewPager2>(R.id.home_viewPager)
        binding.seeMoreBtn.setOnClickListener {
            handleSeeMoreAction()
        }


        return view
    }

    private fun handleSeeMoreAction() {
        // Access the ViewPager2 from the parent activity
        val viewPager = activity?.findViewById<ViewPager2>(R.id.home_viewPager)

        // Check if ViewPager2 is not null
        if (viewPager != null) {
            // Set the current item of ViewPager2 to 2
            viewPager.currentItem = 2

            // Access the BottomNavigationView from the parent activity
            val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)

            // Check if BottomNavigationView is not null
            if (bottomNavView != null) {
                // Update BottomNavigationView selection to match the ViewPager2 item
                bottomNavView.menu.getItem(2).isChecked = true
            }
        }
    }

    private fun handleBreakAction() {

        if (!isOnBreak){

            // Update the text of the TextView
            binding.currentStatusTV.text = getString(R.string.on_break_text)
            binding.currentStatusTV.startAnimation(fadeIn)
            binding.clockOutImg.visibility = View.VISIBLE
            binding.clockOutImg.startAnimation(fadeIn)
            binding.TakeABreakBtn.text = getString(R.string.end_break)



            isOnBreak = true
        }else{

            binding.clockOutImg.visibility = View.GONE
            binding.currentStatusTV.text = getString(R.string.clocked_in_text)
            //binding.currentStatusTV.startAnimation(fadeOut)
            binding.TakeABreakBtn.text = getString(R.string.take_break)
            isOnBreak = false
        }
        // Additional actions specific to Clock Out
    }

    private fun handleClockInAction() {

        // Update the text of the TextView
        binding.currentStatusTV.text = getString(R.string.clocked_in_text)
        binding.clockOutImg.visibility = View.GONE
        //binding.clockInBtn.startAnimation(fadeOut)
        binding.clockInBtn.visibility = View.GONE
        //binding.TakeABreakBtn.startAnimation(fadeIn)
        binding.TakeABreakBtn.visibility = View.VISIBLE
        //binding.clockOutBtn.startAnimation(fadeIn)
        binding.clockOutBtn.visibility = View.VISIBLE


        // Additional actions specific to Clock In
    }

    private fun handleClockOutAction() {

        if (isOnBreak){
            Toast.makeText(requireContext(), "End your break to Clock Out", Toast.LENGTH_SHORT).show()
        }else {
            // Update the text of the TextView
            binding.currentStatusTV.text = getString(R.string.clocked_out_text)
            binding.clockOutImg.visibility = View.VISIBLE
            //binding.TakeABreakBtn.startAnimation(fadeOut)
            binding.TakeABreakBtn.visibility = View.GONE
            //binding.clockOutBtn.startAnimation(fadeOut)
            binding.clockOutBtn.visibility = View.GONE
            //binding.clockInBtn.startAnimation(fadeIn)
            binding.clockInBtn.visibility = View.VISIBLE
        }

        // Additional actions specific to Clock Out
    }

    private fun setTime() {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)

        // Set the formatted time to the TextView
        binding.currentTime.text = formattedTime
    }

    // Function to determine the correct suffix for the day
    private fun getDayOfMonthSuffix(day: Int): String {
        return if (day in 11..13) {
            "th"
        } else {
            when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }

    // Function to format the date with the appropriate suffix
    private fun setDate() {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())

        val day = SimpleDateFormat("d", Locale.getDefault()).format(currentDate).toInt()
        val suffix = getDayOfMonthSuffix(day)
        val year = yearFormat.format(currentDate)

        val formattedDate = "${dateFormat.format(currentDate)}$suffix, $year"

        // Set the formatted date to the TextView
        binding.currentDate.text = formattedDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
