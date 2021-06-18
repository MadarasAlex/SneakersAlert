package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_fill_details.*
import kotlin.properties.Delegates


class FillDetailsFragment : Fragment(R.layout.fragment_fill_details) {
    var selectedYear = 0
    var selectedMonth = 0
    var selectedDay = 0
    var option: String? = null
    lateinit var type: String
    val mAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val databaseUsers = database.getReference("Users")

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
        requireActivity().toolbar.navigationIcon = resources.getDrawable(R.drawable.back, null)
        requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.back)
        requireActivity().toolbar.setNavigationOnClickListener {
            requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            findNavController().navigate(R.id.nav_details)
        }
        selectedType = 1
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->

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
        if (!Global.saved) {
            val nameWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
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
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
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
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
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
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
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
        }
        save_address2.setOnClickListener {
            if (!Global.saved) {

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
                        saveData()
                    findNavController().navigate(R.id.nav_details)
                    if (selectedType == 1) {
                        saveData()
                        findNavController().navigate(R.id.nav_details)
                    }
                }

            } else {
                saveData()

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

    fun saveData() {

        val rootReference = database.reference
        val user = mAuth.currentUser
        val lastNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("lastname")
        val mailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
        val nameReference = rootReference.child("Users").child(user?.uid.toString()).child("name")
        val typeCustomerReference =
            rootReference.child("Users").child(user?.uid.toString()).child("type")
        val phoneReference = rootReference.child("Users").child(user?.uid.toString()).child("phone")
        val savedReference = rootReference.child("Users").child(user?.uid.toString()).child("saved")
        savedReference.setValue(true)
        savedReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.saved = snapshot.value as Boolean
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        if (last_name.text?.isEmpty() == false) {
            mailReference.setValue(last_name.text.toString().trim())

            user!!.verifyBeforeUpdateEmail(last_name.toString())
            user.updateEmail(last_name.toString()).addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    Toast.makeText(activity, "The email updated.", Toast.LENGTH_SHORT).show()
                }
            }

        }

        if (phonemojiText.text?.isEmpty() == false) {
            phoneReference.setValue(
                phonemojiText.initialCountryCode.toString() + phonemojiText.text.toString().trim()
            )
            Global.phone =
                phonemojiText.initialCountryCode.toString() + phonemojiText.text.toString().trim()
        }
        typeCustomerReference.setValue(selectedType)
        if (email.text?.isEmpty() == false) {
            lastNameReference.setValue(email.text.toString().trim())
            Global.lastName = email.text.toString().trim()
        }
        if (!Global.saved)
            Global.type = selectedType
        if (Global.type == 2) {
            val companyNameReference =
                rootReference.child("Users").child(user?.uid.toString()).child(
                    "company_name"
                )
            val vatReference = rootReference.child("Users").child(user?.uid.toString()).child("VAT")
            val taxNumberReference =
                rootReference.child("Users").child(user?.uid.toString()).child("tax_number")
            if (company_name.text?.isEmpty() == false) {
                Global.companyName = company_name_section.text.toString().trim()
                companyNameReference.setValue(company_name_section.text.toString().trim())
            }
            if (vat_section.text?.isEmpty() == false) {
                vatReference.setValue(vat_section.text.toString().trim())
                Global.vat = vat_section.text.toString().trim()
            }
            if (vat_section.text?.isEmpty() == false) {
                taxNumberReference.setValue(tax_number_section.text.toString().trim())
                Global.tax = tax_number_section.text.toString().trim()
            }

            Global.saved = true
        }
    }

    override fun onStart() {
        super.onStart()
        your_name.text = Global.username + " " + Global.lastName
        account_fill_country.text = Global.country
    }

    override fun onResume() {
        super.onResume()
        your_name.text = Global.username + " " + Global.lastName
        account_fill_country.text = Global.country

    }

    fun getType() {
        val type = databaseUsers.child(id.toString()).child("type")

        type.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.type = snapshot.value
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}