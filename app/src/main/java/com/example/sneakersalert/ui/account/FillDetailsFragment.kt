package com.example.sneakersalert.ui.account

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_fill_details.*
import kotlinx.android.synthetic.main.fragment_request_sneaker.*
import kotlin.properties.Delegates


class FillDetailsFragment : Fragment(R.layout.fragment_fill_details) {
    var selectedYear = 0
    var selectedMonth = 0
    var selectedDay = 0
    var option: String? = null
    lateinit var type: String
    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val databaseUsers = database.getReference("Users")

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
                getSavedStatus()
                if (!Global.saved)
                    businessWatchers()
            } else {
                selectedType = 1
            }
        }
        getBirthday()
        getSavedStatus()
        getPhoneNumber()

        if (!Global.saved) {
            watchers()
        }
        getFirstName()
        getCountry()
        getType()
        getLastName()
        getPhoneNumber()
        getBirthday()
        selectedType = Global.type.toInt()
        name_section.setText(Global.firstName)
        last_name.setText(mAuth.currentUser?.email)
        email.setText(Global.lastName)
        if(last_name.text.toString().trim()!=mAuth.currentUser!!.email.toString())
        {

            Global.logged=false
            mAuth.signOut()
            findNavController().navigate(R.id.nav_menuLogin)
        }

        company_name_section.setText(Global.companyName)
        println("Phone: ${Global.phone}")
        if (Global.type == "1")
            autoCompleteTextView.setText("Personal")
        else autoCompleteTextView.setText("Business")
        getCompanyName()
        getVAT()
        getTax()
        vat_section.setText(Global.vat)
        tax_number_section.setText(Global.tax)
        company_name_section.setText(Global.companyName)
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
            }
            saveData()
        }
    }

    override fun onStart() {
        super.onStart()
        if(last_name.text.toString().trim()!=mAuth.currentUser!!.email.toString())
        {
            Global.logged=false
            mAuth.signOut()
            findNavController().navigate(R.id.nav_menuLogin)
        }
        your_name.text = Global.firstName + " " + Global.lastName
        getPhoneNumber()
        getVAT()
        getTax()
        account_fill_country.text = Global.country
    }

    override fun onResume() {
        super.onResume()
        if(last_name.text.toString().trim()!=mAuth.currentUser!!.email.toString()) {
            Global.logged=false
            mAuth.signOut()
            findNavController().navigate(R.id.nav_menuLogin)
        }
        getPhoneNumber()
        getVAT()
        getTax()
        your_name.text = Global.username + " " + Global.lastName
        account_fill_country.text = Global.country
    }

    private fun watchers() {
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

    private fun businessWatchers() {
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
    }

    private fun isLeap(year: Int): Boolean {
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

    private fun saveData() {
        val rootReference = database.reference
        val user = mAuth.currentUser
        val lastNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("lastname")
        val mailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
        val nameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("firstname")
        val birthdayReference =
            rootReference.child("Users").child(user?.uid.toString()).child("birthday")
        val typeCustomerReference =
            rootReference.child("Users").child(user?.uid.toString()).child("type")
        val phoneReference = rootReference.child("Users").child(user?.uid.toString()).child("phone")
        val savedReference = rootReference.child("Users").child(user?.uid.toString()).child("saved")
        val companyNameReference =
            rootReference.child("Users").child(user?.uid.toString()).child("company_name")
        val vatReference = rootReference.child("Users").child(user?.uid.toString()).child("VAT")
        val taxNumberReference =
            rootReference.child("Users").child(user?.uid.toString()).child("tax_number")
        nameReference.setValue(name_section.text.toString().trim())
        birthdayReference.setValue(autoCompleteDay.text.toString() + "/" + autoCompleteMonth.text.toString() + "/" + autoCompleteYear.text.toString())
        user!!.verifyBeforeUpdateEmail(last_name.toString())
        phoneReference.setValue(phoneText.text.toString().trim())
        lastNameReference.setValue(email.text.toString().trim())
        getSavedStatus()
        if (!Global.saved)
            Global.type = selectedType.toString()
        else getType()
        typeCustomerReference.setValue(selectedType.toString())
        companyNameReference.setValue(company_name_section.text.toString().trim())
        vatReference.setValue(vat_section.text.toString().trim())
        taxNumberReference.setValue(tax_number_section.text.toString().trim())
        Global.companyName = company_name_section.text.toString().trim()
        Global.vat = vat_section.text.toString().trim()
        Global.phone = phoneText.text.toString().trim()
        Global.firstName = name_section.text.toString().trim()
        Global.lastName = email.text.toString().trim()
        Global.tax = tax_number_section.text.toString().trim()
        Global.saved = true
        Global.birthday =
            autoCompleteDay.text.toString() + "/" + autoCompleteMonth.text.toString() + "/" + autoCompleteYear.text.toString()
        savedReference.setValue(true)
        if(last_name_section.text.toString()!=user.email) {
            updateEmail()
            user.updateEmail(last_name.text.toString().trim()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email successful updated!")
                }
            }
            mailReference.setValue(last_name.text.toString().trim())
            mailReference.push()
        }
        phoneReference.push()
        nameReference.push()
        lastNameReference.push()
        typeCustomerReference.push()
        companyNameReference.push()
        vatReference.push()
        taxNumberReference.push()
        birthdayReference.push()
    }

    private fun getType() {
        val type = databaseUsers.child(id.toString()).child("type")
        type.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    Global.type = snapshot.getValue(String::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getPhoneNumber() {
        val rootReference = database.reference
        val user = mAuth.currentUser
        val phoneNumber = rootReference.child("Users").child(user?.uid.toString()).child("phone")
        phoneNumber.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.phone = snapshot.value.toString()
                println(snapshot.value)
                println("Phone: ${Global.phone}")
                phoneText.setText(snapshot.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun getCompanyName() {
        if (Global.type != "1") {
            val companyName = databaseUsers.child(id.toString()).child("company_name")

            companyName.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Global.companyName = snapshot.value.toString()
                        company_name_section.setText(Global.companyName)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }
    }

    private fun getLastName() {
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Global.lastName = snapshot.value.toString()
                    println("Name:${Global.lastName}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getVAT() {

        getType()
        if (Global.type != "1") {
            val rootReference = database.reference
            val user = mAuth.currentUser
            val vat = rootReference.child("Users").child(user?.uid.toString()).child("VAT")
            vat.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Global.vat = snapshot.value.toString()
                    println(snapshot.value)
                    vat_section.setText(snapshot.value.toString())

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
    }

    private fun getTax() {
        getType()
        if (Global.type != "1") {
            val rootReference = database.reference
            val user = mAuth.currentUser
            val tax = rootReference.child("Users").child(user?.uid.toString()).child("tax_number")
            tax.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Global.tax = snapshot.value.toString()
                    println(snapshot.value)
                    tax_number_section.setText(Global.tax)
                }


                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            })

        }
    }

    private fun getSavedStatus() {
        val rootReference = database.reference
        val user = mAuth.currentUser
        val savedReference = rootReference.child("Users").child(user?.uid.toString()).child("saved")
        savedReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.saved = snapshot.value as Boolean
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
            }
        })
    }

    private fun getCountry() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val country = databaseUsers.child(id.toString()).child("country")
        country.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.country = snapshot.value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
            }

        })
    }

    private fun getFirstName() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val username = databaseUsers.child(id.toString()).child("firstname")
        username.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.firstName = snapshot.value.toString()
                name_section.setText(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
            }
        })
    }

    private fun getBirthday() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val birthday = databaseUsers.child(id.toString()).child("birthday")
        birthday.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Global.birthday = snapshot.value.toString()
                    var day: String = ""
                    var month = ""
                    var year = ""
                    var k = 0
                    var p = 0
                    for (i in Global.birthday.indices) {

                        if (Global.birthday[i].toString() == "/") {
                            k = i
                            break
                        } else day = day.plus(Global.birthday[i])
                    }
                    for (j in k + 1 until Global.birthday.length) {
                        if (Global.birthday[j].toString() == "/") {
                            j.also { p = it }
                            break
                        } else month = month.plus(Global.birthday[j])
                    }
                    for (i in p + 1 until Global.birthday.length) {
                        year = year.plus(Global.birthday[i])
                    }
                    autoCompleteDay.setText(day)
                    autoCompleteMonth.setText(month)
                    autoCompleteYear.setText(year)
                    println(k)
                    println(p)
                    println("Birthday: $day,$month,$year")
                }
                birthdayConfig()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
            }
        })
    }

    private fun birthdayConfig() {
        val years = ArrayList<Int>()
        years.clear()
        for (i in 2021 downTo 1900)
            years.add(i)
        val adapter4 =
            ArrayAdapter(this.requireActivity(), android.R.layout.simple_dropdown_item_1line, years)
        autoCompleteYear.setAdapter(adapter4)
        drop_year.setOnClickListener {
            autoCompleteYear.showDropDown()
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
                        android.R.layout.simple_dropdown_item_1line,
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

    }
    private fun updateEmail()
    {
        var user = FirebaseAuth.getInstance().currentUser
        // Get auth credentials from the user for re-authentication
        // Get auth credentials from the user for re-authentication
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        var pass=""
        val username = databaseUsers.child(id.toString()).child("password")

        username.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pass=snapshot.value.toString()
                val credential = EmailAuthProvider
                    .getCredential(user?.email.toString(),pass) // Current Login Credentials \\

                // Prompt the user to re-provide their sign-in credentials
                // Prompt the user to re-provide their sign-in credentials
                user!!.reauthenticate(credential)
                    .addOnCompleteListener {
                        Log.d(TAG, "User re-authenticated.")
                        //Now change your email address \\
                        //----------------Code for Changing Email Address----------\\
                        val user2=mAuth.currentUser
                        if (user2 != null) {
                            user2.updateEmail(last_name.text.toString().trim())
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User email address updated.")
                                    }
                                }
                            val rootReference = database.reference
                            val user = mAuth.currentUser
                            val mailReference = rootReference.child("Users").child(user?.uid.toString()).child("mail")
                            mailReference.setValue(last_name.text.toString().trim())
                            mailReference.push()

                        }
                        //----------------------------------------------------------\\
                    }
                mAuth.signOut()
                findNavController().navigate(R.id.nav_menuLogin)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
            }
        })
        println(pass)

    }

}