package com.sovereign.clockify.Dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sovereign.clockify.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Dashboard : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set the formatted date with suffix
        setFormattedDateWithSuffix()

        return view
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
    private fun setFormattedDateWithSuffix() {
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
