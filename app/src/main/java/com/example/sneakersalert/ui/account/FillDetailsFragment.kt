package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.fragment_fill_details.*
import kotlin.properties.Delegates

class FillDetailsFragment : Fragment(R.layout.fragment_fill_details) {
    var selectedYear by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profile = resources.getStringArray(R.array.profiles)
        val adapter = ArrayAdapter(
            this.requireActivity(),
            android.R.layout.simple_list_item_1, profile
        )
        autoCompleteTextView.threshold = 0
        autoCompleteTextView.setAdapter(adapter)
        drop.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }

        val years = ArrayList<Int>()
        for (i in 2021 downTo 1900)
            years.add(i)
        val adapter4 =
            ArrayAdapter(this.requireActivity(), android.R.layout.simple_list_item_1, years)
        autoCompleteYear.threshold = 0
        autoCompleteYear.setAdapter(adapter4)
        println("2012: ${isLeap(2012)} ")
        println("2013: ${isLeap(2013)} ")
        drop_year.setOnClickListener {
            autoCompleteYear.showDropDown()
            autoCompleteYear.threshold = 0
            autoCompleteYear.setOnItemClickListener { parent, _, position, _ ->
                selectedYear = parent.getItemAtPosition(position).toString().toInt()
                val months = arrayListOf(
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
                )
                val adapter2 =
                    ArrayAdapter(
                        this.requireActivity(),
                        android.R.layout.simple_list_item_1,
                        months
                    )
                autoCompleteMonth.setAdapter(adapter2)
                var option: String? = null
                drop_month.setOnClickListener {
                    autoCompleteMonth.showDropDown()
                    autoCompleteMonth.threshold = 0
                    autoCompleteMonth.setOnItemClickListener { parent, _, position, id ->
                        option = parent.getItemAtPosition(position).toString()
                        println(option)
                        val days = ArrayList<Int>()
                        if (option == "January" || option == "March" || option == "May" || option == "July"
                            || option == "August" || option == "October" || option == "December"
                        ) {
                            for (i in 1..31)
                                days.add(i)
                        } else if (option == "April" || option == "June" || option == "September" || option == "November") {
                            for (i in 1..30)
                                days.add(i)
                        } else if (option == "February") {
                            if (isLeap(selectedYear))
                                for (i in 1..29)
                                    days.add(i)
                            else for (i in 1..28)
                                days.add(i)
                        }
                        println(option)
                        val adapter3 =
                            ArrayAdapter(
                                this.requireActivity(),
                                android.R.layout.simple_list_item_1,
                                days
                            )
                        autoCompleteDay.setAdapter(adapter3)
                        autoCompleteDay.threshold = 0
                        drop_day.setOnClickListener {
                            autoCompleteDay.showDropDown()
                        }
                    }

                }
            }

        }

        save_address.setOnClickListener {
            findNavController().navigate(R.id.nav_details)
        }
    }

    fun isLeap(year: Int): Boolean {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                // year is divisible by 400, hence the year is a leap year
                if (year % 400 == 0)
                    return true
            } else
                return true
        }
        return false


    }

}