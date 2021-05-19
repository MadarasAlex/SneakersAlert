package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fill_details.*
import kotlin.properties.Delegates

class FillDetailsFragment : Fragment(R.layout.fragment_fill_details) {
    var selectedYear by Delegates.notNull<Int>()
    var selectedMonth by Delegates.notNull<Int>()
    var selectedDay by Delegates.notNull<Int>()
    var option: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profile = resources.getStringArray(R.array.profiles)
        val adapter = ArrayAdapter(
            this.requireActivity(),
            android.R.layout.simple_list_item_1, profile
        )
        val constraint = frameLayout3
        val cset = ConstraintSet()
        business.visibility = View.GONE
        checkbox2.visibility = View.GONE
        checkbox.visibility = View.VISIBLE
        save_address2.visibility = View.GONE
        save_address.visibility = View.VISIBLE
        cset.connect(
            R.id.checkbox,
            ConstraintSet.TOP,
            R.id.password_fill,
            ConstraintSet.BOTTOM
        )
        cset.constrainDefaultHeight(R.id.frameLayout3, ConstraintSet.WRAP_CONTENT)
        cset.applyTo(constraint)
        autoCompleteTextView.threshold = 0
        autoCompleteTextView.setAdapter(adapter)
        drop.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }
        autoCompleteTextView.setOnItemClickListener { parent, _, position, id ->
            val type = parent.getItemAtPosition(position).toString()
            if (type == "Business") {
                val constraint = frameLayout3
                val cset = ConstraintSet()
                business.visibility = View.VISIBLE
                checkbox2.visibility = View.GONE
                checkbox.visibility = View.VISIBLE
                save_address2.visibility = View.VISIBLE
                save_address.visibility = View.GONE
                cset.connect(R.id.checkbox2, ConstraintSet.TOP, R.id.business, ConstraintSet.BOTTOM)
                cset.constrainDefaultHeight(R.id.frameLayout3, ConstraintSet.WRAP_CONTENT)
                cset.applyTo(constraint)

            } else {
                val constraint = frameLayout3
                val cset = ConstraintSet()
                business.visibility = View.GONE
                checkbox2.visibility = View.GONE
                checkbox.visibility = View.VISIBLE
                save_address2.visibility = View.GONE
                save_address.visibility = View.VISIBLE
                cset.connect(
                    R.id.checkbox,
                    ConstraintSet.TOP,
                    R.id.password_fill,
                    ConstraintSet.BOTTOM
                )
                cset.constrainDefaultHeight(R.id.frameLayout3, ConstraintSet.WRAP_CONTENT)
                cset.applyTo(constraint)
            }
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

                drop_month.setOnClickListener {
                    autoCompleteMonth.showDropDown()
                    autoCompleteMonth.threshold = 0
                    autoCompleteMonth.setOnItemClickListener { parent, _, position, id ->
                        option = parent.getItemAtPosition(position).toString()
                        println(option)
                        val days = ArrayList<Int>()
                        val adapter3 =
                            ArrayAdapter(
                                this.requireActivity(),
                                android.R.layout.simple_list_item_1,
                                days
                            )
                        autoCompleteDay.setAdapter(adapter3)
                        autoCompleteDay.threshold = 0
                        drop_day.setOnClickListener {
                            days.clear()
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
                                    for (i in 1..29) {
                                        days.add(i)
                                    }
                                else for (i in 1..28)
                                    days.add(i)
                            }
                            autoCompleteDay.showDropDown()
                            autoCompleteDay.setOnItemClickListener { parent, view, position, id ->
                                selectedDay = parent.getItemAtPosition(position).toString().toInt()
                                if (option == "April" || option == "June" || option == "September" || option == "November")
                                    if (selectedDay > 30)
                                        invalid_birthday.visibility = View.VISIBLE
                                    else invalid_birthday.visibility = View.INVISIBLE
                                if (!isLeap(selectedYear) && option == "February")
                                    if (selectedDay > 28)
                                        invalid_birthday.visibility = View.VISIBLE
                                    else invalid_birthday.visibility = View.INVISIBLE

                            }
                        }
                    }


                }


            }

        }

        save_address.setOnClickListener {
            if (option == "April" || option == "June" || option == "September" || option == "November")
                if (selectedDay > 30)
                    invalid_birthday.visibility = View.VISIBLE
                else invalid_birthday.visibility = View.INVISIBLE
            if (!isLeap(selectedYear) && option == "February")
                if (selectedDay > 28)
                    invalid_birthday.visibility = View.VISIBLE
                else invalid_birthday.visibility = View.INVISIBLE
            if (invalid_birthday.visibility == View.INVISIBLE)
                findNavController().navigate(R.id.nav_details)

        }
        save_address2.setOnClickListener {
            if (option == "April" || option == "June" || option == "September" || option == "November")
                if (selectedDay > 30)
                    invalid_birthday.visibility = View.VISIBLE
                else invalid_birthday.visibility = View.INVISIBLE
            if (!isLeap(selectedYear) && option == "February")
                if (selectedDay > 28)
                    invalid_birthday.visibility = View.VISIBLE
                else invalid_birthday.visibility = View.INVISIBLE
            if (invalid_birthday.visibility == View.INVISIBLE)
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

    override fun onStart() {
        super.onStart()
        val constraint = frameLayout3
        val cset = ConstraintSet()
        business.visibility = View.GONE
        checkbox2.visibility = View.GONE
        checkbox.visibility = View.VISIBLE
        save_address2.visibility = View.GONE
        save_address.visibility = View.VISIBLE
        cset.connect(
            R.id.checkbox,
            ConstraintSet.TOP,
            R.id.password_fill,
            ConstraintSet.BOTTOM
        )
        cset.constrainDefaultHeight(R.id.frameLayout3, ConstraintSet.WRAP_CONTENT)
        cset.applyTo(constraint)
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(
                R.id.nav_orders,
                true,
                false
            )
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true

        }
    }

}