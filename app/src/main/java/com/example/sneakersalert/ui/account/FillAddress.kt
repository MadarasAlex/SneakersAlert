package com.example.sneakersalert.ui.account

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.Global
import com.example.sneakersalert.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_fill_address.*
import kotlinx.android.synthetic.main.fragment_fill_address.country_selector3
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.*
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.fullname_section
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.house_number_section
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.place_section
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.postal_section
import kotlinx.android.synthetic.main.fragment_fill_shipping_address.streetname_section

class FillAddress : Fragment(R.layout.fragment_fill_address) {
    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val databaseUsers = database.getReference("Users")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true
        }
        requireActivity().toolbar.navigationIcon = resources.getDrawable(R.drawable.back, null)
        requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        requireActivity().actionBar?.setHomeAsUpIndicator(R.drawable.back)
        requireActivity().toolbar.setNavigationOnClickListener {
            requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            findNavController().navigate(R.id.nav_details)
        }
        val ccp = view.findViewById<CountryPickerView>(R.id.country_selector3)
        val countries = mutableListOf<CPCountry>()
        for (el in ccp.cpViewHelper.cpDataStore.countryList)
            if ((el.currencyName == "Euro"
                        || el.currencyName == "Romanian New Lei"
                        || el.currencyName == "Hungarian Forint"
                        || el.currencyName == "Czech Koruna"
                        || el.currencyName == "Swedish Krona"
                        || el.currencyName == "Croatian Kuna"
                        || el.currencyName == "Polish Zloty"
                        || el.currencyName == "Bulgarian Lev")
                && (el.name != "Montenegro"
                        && el.name != "Saint Martin (French Part)"
                        && el.name != "Saint Pierre and Miquelon"
                        && el.name != "Martinique"
                        && el.name != "Mayotte"
                        && el.name != "Saint Barthelemy"
                        && el.name != "Guadeloupe"
                        && el.name != "French Guiana"
                        && el.name != "French Southern Territories"
                        && el.name != "Holy See"
                        && el.name != "San Marino"
                        && el.name != "Lichtenstein"
                        && el.name != "Réunion"
                        && el.name != "Åland Islands"
                        )

            ) {
                countries.add(el)
            }
        ccp.cpViewHelper.cpDataStore.countryList.removeAll { true }
        ccp.cpViewHelper.cpDataStore.countryList = countries
        ccp.cpViewHelper.cpListConfig.preferredCountryCodes = "NL,RO"
        watchers()
        getFirstName()
        getLastName()
        getSavedStatus()
        getCityInv()
        getCountryInv()
        getHouseNumberInv()
        getStreetInv()
        getZIPInv()
        getExtraInv()
        house_number_section.setText(Global.houseNumberInv.toString())
        fullname_section.setText(Global.firstName + " " + Global.lastName)
        postal_section.setText(Global.zipInv)
        streetname_section.setText(Global.streetInv)
        view?.findViewById<TextInputEditText>(R.id.extra_section)?.setText(Global.extraInv)
        place_section.setText(Global.cityInv)
        for (el in country_selector3.cpViewHelper.cpDataStore.countryList) {
            if (el.englishName == Global.country) {
                println(el.englishName)
                country_selector3.tvCountryInfo.text = el.englishName
                country_selector3.tvEmojiFlag.text = el.flagEmoji
            }
        }

        if (!Global.saved2)
            watchers()
        else {
            getCityInv()
            getCountryInv()
            getHouseNumberInv()
            getStreetInv()
            getZIPInv()
            getExtraInv()
        }
        val save = view.findViewById<Button>(R.id.save_address)
        save.setOnClickListener {
            if (!Global.saved3) {
                if (fullname_section.text?.isEmpty()!!) {
                    req_field20.visibility = View.VISIBLE
                    fullname_section.error = getString(R.string.error)
                } else {
                    req_field20.visibility = View.INVISIBLE
                    fullname_section.error = null
                }
                if (postal_section.text?.isEmpty()!!) {
                    req_field21.visibility = View.VISIBLE
                    postal_section.error = getString(R.string.error)
                } else {
                    req_field21.visibility = View.INVISIBLE
                    postal_section.error = null
                }
                if (house_number_section.text?.isEmpty()!!) {
                    req_field22.visibility = View.VISIBLE
                    house_number_section.error = getString(R.string.error)
                } else {
                    req_field22.visibility = View.INVISIBLE
                    house_number_section.error = null
                }
                if (streetname_section.text?.isEmpty()!!) {
                    req_field23.visibility = View.VISIBLE
                    streetname_section.error = getString(R.string.error)
                } else {
                    req_field23.visibility = View.INVISIBLE
                    streetname_section.error = null
                }
                if (place_section.text?.isEmpty()!!) {
                    req_field24.visibility = View.VISIBLE
                    place_section.error = getString(R.string.error)
                } else {
                    req_field24.visibility = View.INVISIBLE
                    place_section.error = null
                }
                if (country_selector3.equals("")) req_field25.visibility = View.VISIBLE
                else req_field25.visibility = View.INVISIBLE
            }
            saveData()
        }
    }

    private fun saveData() {
        val rootReference = database.reference
        val user = mAuth.currentUser
        val streetReference =
            rootReference.child("Users").child(user?.uid.toString()).child("street_invoice")
        val countryReference =
            rootReference.child("Users").child(user?.uid.toString()).child("country_invoice")
        val zipCodeReference =
            rootReference.child("Users").child(user?.uid.toString()).child("postal_invoice")
        val cityReference = rootReference.child("Users").child(user?.uid.toString()).child("city_invoice")
        val savedReference =
            rootReference.child("Users").child(user?.uid.toString()).child("saved_3")
        val houseNumberReference =
            rootReference.child("Users").child(user?.uid.toString()).child("house_number_invoice")
        val extraReference =
            rootReference.child("Users").child(user?.uid.toString()).child("extra_address_invoice")
        getSavedStatus()
        country_selector3.cpViewHelper.selectedCountry.observe(
            viewLifecycleOwner,
            { selectedCountry: CPCountry? ->
                if (selectedCountry != null) {
                    countryReference.setValue(selectedCountry.name.trim())
                }
            }
        )
        streetReference.setValue(streetname_section.text.toString().trim())
        extraReference.setValue(view?.findViewById<TextInputEditText>(R.id.extra_section)?.text.toString().trim())
        zipCodeReference.setValue(postal_section.text.toString().trim())
        cityReference.setValue(place_section.text.toString().trim())
        houseNumberReference.setValue(house_number_section.text.toString().toInt())

        Global.zipInv=postal_section.text.toString().trim()
        Global.houseNumberInv=house_number_section.text.toString().toInt()
        Global.cityInv=place_section.text.toString().trim()
        Global.streetInv=streetname_section.text.toString().trim()
        Global.extraInv=view?.findViewById<TextInputEditText>(R.id.extra_section)?.text.toString().trim()
        Global.saved3 = true

        savedReference.setValue(true)
        extraReference.push()
        streetReference.push()
        zipCodeReference.push()
        cityReference.push()
        houseNumberReference.push()
        savedReference.push()
    }

    private fun watchers() {
        val fullnameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    //         req_field9.visibility = View.VISIBLE
                    fullname_section.error = getString(R.string.error)
                } else {
//                    req_field9.visibility=View.INVISIBLE
                    fullname_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        val postalWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    //      req_field10.visibility = View.VISIBLE
                    postal_section.error = getString(R.string.error)
                } else {
                    //      req_field10.visibility=View.INVISIBLE
                    postal_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        val houseWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    //        req_field11.visibility = View.VISIBLE
                    house_number_section.error = getString(R.string.error)
                } else {
                    //    req_field11.visibility = View.INVISIBLE
                    house_number_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        val streetnameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    //     req_field12.visibility = View.VISIBLE
                    streetname_section.error = getString(R.string.error)
                } else {
                    //     req_field12.visibility=View.INVISIBLE
                    streetname_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        val placeWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    place_section.error = getString(R.string.error)
                    //      req_field13.visibility = View.VISIBLE
                } else {
                    //      req_field13.visibility = View.INVISIBLE
                    place_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        fullname_section.addTextChangedListener(fullnameWatcher)
        postal_section.addTextChangedListener(postalWatcher)
        house_number_section.addTextChangedListener(houseWatcher)
        streetname_section.addTextChangedListener(streetnameWatcher)
        place_section.addTextChangedListener(placeWatcher)
    }

    override fun onStart() {
        super.onStart()
        view?.findViewById<TextView>(R.id.your_name)?.text = Global.username
        view?.findViewById<TextView>(R.id.account_fill_country)?.text= Global.country
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<TextView>(R.id.your_name)?.text = Global.username
        view?.findViewById<TextView>(R.id.account_fill_country)?.text= Global.country

    }
    private fun getSavedStatus() {
        val rootReference = database.reference
        val user = mAuth.currentUser
        val savedReference =
            rootReference.child("Users").child(user?.uid.toString()).child("saved_3")
        savedReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.saved3 = snapshot.value as Boolean
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getCountryInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val country = databaseUsers.child(id.toString()).child("country_invoice")
        country.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.countryInv = snapshot.value.toString()
                country_selector3.tvCountryInfo.text = snapshot.value.toString()
                println(Global.country)
                for (el in country_selector3.cpViewHelper.cpDataStore.countryList) {
                    if (el.englishName == snapshot.value) {
                        println(snapshot.value)
                        println(el.englishName)
                        country_selector3.tvCountryInfo.text = el.englishName
                        country_selector3.tvEmojiFlag.text = el.flagEmoji
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getStreetInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val street = databaseUsers.child(id.toString()).child("street_invoice")
        street.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.streetInv = snapshot.value.toString()
                if(snapshot.value.toString()=="" || snapshot.value.toString()=="null" ||snapshot.value.toString().isEmpty())
                    streetname_section.setText("")
                else streetname_section.setText(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getCityInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val city = databaseUsers.child(id.toString()).child("city_invoice")
        city.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Global.cityInv = snapshot.value.toString()
                if(snapshot.value.toString()=="" || snapshot.value.toString()=="null" ||snapshot.value.toString().isEmpty())
                    place_section.setText("")
                place_section.setText(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getZIPInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val zip = databaseUsers.child(id.toString()).child("postal_invoice")
        zip.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Global.zipInv = snapshot.value.toString()
                    postal_section.setText(snapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getHouseNumberInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val houseNumber = databaseUsers.child(id.toString()).child("house_number_invoice")
        houseNumber.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    Global.houseNumberInv = snapshot.value.toString().toInt()
                    house_number_section.setText(snapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getExtraInv() {
        val databaseUsers = database.getReference("Users")
        val id = mAuth.currentUser?.uid
        val extra = databaseUsers.child(id.toString()).child("extra_address_invoice")
        extra.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    Global.extraInv = snapshot.value.toString()
                    view?.findViewById<TextInputEditText>(R.id.extra_section)?.setText(snapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
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
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.toString())
            }
        })
    }

    private fun getLastName() {
        val lastName = databaseUsers.child(id.toString()).child("lastname")
        lastName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Global.lastName = snapshot.value.toString()
                    fullname_section.setText(Global.firstName + " " + Global.lastName)
                    fullname_section.isEnabled = false
                    fullname_section.isFocusableInTouchMode = true
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}