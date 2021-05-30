package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import kotlinx.android.synthetic.main.fragment_fill_details.*
import kotlin.properties.Delegates

class FillDetailsFragment : Fragment(R.layout.fragment_fill_details) {
    var selectedYear = 0
    var selectedMonth = 0
    var selectedDay = 0
    var option: String? = null
    lateinit var type: String
    var selectedType by Delegates.notNull<Int>()

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
        selectedType = 1
        autoCompleteTextView.setOnItemClickListener { parent, _, position, id ->

            type = parent.getItemAtPosition(position).toString()
            if (type == "Business") {
                selectedType = 2
                val companyWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s!!.isEmpty() || s == "") {
                            req_field5.visibility = View.VISIBLE
                            company_name_section.error = getString(R.string.error)
                        } else {
                            req_field5.visibility = View.INVISIBLE
                            company_name_section.error = null
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                }
                val vatWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s!!.isEmpty() || s == "") {
                            req_field6.visibility = View.VISIBLE
                            vat_section.error = getString(R.string.error)
                        } else {
                            req_field6.visibility = View.INVISIBLE
                            vat_section.error = null
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                }
                val taxWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s!!.isEmpty() || s == "") {
                            req_field7.visibility = View.VISIBLE
                            tax_number_section.error = getString(R.string.error)
                        } else {
                            req_field7.visibility = View.INVISIBLE
                            tax_number_section.error = null
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                }
                company_name_section.addTextChangedListener(companyWatcher)
                tax_number_section.addTextChangedListener(taxWatcher)
                vat_section.addTextChangedListener(vatWatcher)
            } else {
                selectedType = 1
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
        val nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    req_field.visibility = View.VISIBLE
                    name_section.error = getString(R.string.error)
                } else {
                    req_field.visibility = View.INVISIBLE
                    name_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        val lastNameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    req_field2.visibility = View.VISIBLE
                    email.error = getString(R.string.error)
                } else {
                    req_field2.visibility = View.INVISIBLE
                    email.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    req_field3.visibility = View.VISIBLE
                    last_name.error = getString(R.string.error)
                } else {
                    req_field3.visibility = View.INVISIBLE
                    last_name.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        val typeWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "")
                    req_field8.visibility = View.VISIBLE
                else req_field8.visibility = View.INVISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        name_section.addTextChangedListener(nameWatcher)
        email.addTextChangedListener(lastNameWatcher)
        last_name.addTextChangedListener(emailWatcher)
        autoCompleteTextView.addTextChangedListener(typeWatcher)
        save_address2.setOnClickListener {

            if (name_section.text?.isEmpty() == true) {
                req_field.visibility = View.VISIBLE
                name_section.error = getString(R.string.error)
            } else {
                req_field.visibility = View.INVISIBLE
                name_section.error = null
            }
            if (email.text?.isEmpty() == true) {
                req_field2.visibility = View.VISIBLE
                email.error = getString(R.string.error)
            } else {
                req_field2.visibility = View.INVISIBLE
                email.error = null
            }
            if (last_name.text?.isEmpty() == true) {
                req_field3.visibility = View.VISIBLE
                last_name.error = getString(R.string.error)
            } else {
                req_field3.visibility = View.INVISIBLE
                last_name.error = null
            }
            if (autoCompleteTextView.text.isEmpty()) req_field8.visibility = View.VISIBLE
            else req_field8.visibility = View.INVISIBLE
            if (autoCompleteDay.text.isEmpty() || autoCompleteMonth.text.isEmpty() || autoCompleteYear.text.isEmpty())
                invalid_birthday.visibility = View.VISIBLE
            else {
                if (option == "April" || option == "June" || option == "September" || option == "November")
                    if (selectedDay > 30)
                        invalid_birthday.visibility = View.VISIBLE
                    else invalid_birthday.visibility = View.INVISIBLE
                else if (option == "February" && selectedDay > 29)
                    invalid_birthday.visibility = View.VISIBLE
                else {
                    if (!isLeap(selectedYear) && option == "February")
                        if (selectedDay > 28)
                            invalid_birthday.visibility = View.VISIBLE
                        else invalid_birthday.visibility = View.INVISIBLE
                }
            }
            println(selectedType)
            if (selectedType == 2) {
                if (company_name_section.text?.isEmpty() == true) {
                    company_name_section.error = getString(R.string.error)
                    req_field5.visibility = View.VISIBLE
                } else {
                    req_field5.visibility = View.INVISIBLE
                    company_name_section.error = null
                }
                if (vat_section.text?.isEmpty() == true) {
                    vat_section.error = getString(R.string.error)
                    req_field6.visibility = View.VISIBLE
                } else {
                    req_field6.visibility = View.INVISIBLE
                    vat_section.error = null
                }
                if (tax_number_section.text?.isEmpty() == true) {
                    tax_number_section.error = getString(R.string.error)
                    req_field7.visibility = View.VISIBLE
                } else {
                    req_field7.visibility = View.INVISIBLE
                    tax_number_section.error = null
                }
            }
            if (invalid_birthday.visibility == View.INVISIBLE && req_field.visibility == View.INVISIBLE && req_field2.visibility == View.INVISIBLE && req_field3.visibility == View.INVISIBLE && req_field4.visibility == View.INVISIBLE && req_field8.visibility == View.INVISIBLE) {
                if (selectedType == 2 && req_field5.visibility == View.INVISIBLE && req_field6.visibility == View.INVISIBLE && req_field7.visibility == View.INVISIBLE)
                    findNavController().navigate(R.id.nav_details)
                if (selectedType == 1) findNavController().navigate(R.id.nav_details)
            }

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