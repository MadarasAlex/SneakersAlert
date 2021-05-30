package com.example.sneakersalert.ui.account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sneakersalert.R
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fill_invoice_address.*


class FillInvoiceAddress : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fill_invoice_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!requireActivity().navigationView.menu.findItem(R.id.nav_orders).isChecked) {
            requireActivity().navigationView.menu.setGroupCheckable(R.id.gr, true, false)
            requireActivity().navigationView.menu.setGroupCheckable(R.id.nav_orders, true, false)
            requireActivity().navigationView.menu.getItem(2).isCheckable = true
            requireActivity().navigationView.menu.getItem(2).isChecked = true

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
                        || el.currencyName == "Polish Zloty" || el.currencyName == "Bulgarian Lev"
                        )
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
        val fullnameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty() || s == "") {
                    req_field9.visibility = View.VISIBLE
                    fullname_section.error = getString(R.string.error)
                } else {
                    req_field9.visibility = View.INVISIBLE
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
                    req_field10.visibility = View.VISIBLE
                    postal_section.error = getString(R.string.error)
                } else {
                    req_field10.visibility = View.INVISIBLE
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
                    req_field11.visibility = View.VISIBLE
                    house_number_section.error = getString(R.string.error)
                } else {
                    req_field11.visibility = View.INVISIBLE
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
                    req_field12.visibility = View.VISIBLE
                    streetname_section.error = getString(R.string.error)
                } else {
                    req_field12.visibility = View.INVISIBLE
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
                    req_field13.visibility = View.VISIBLE
                } else {
                    req_field13.visibility = View.INVISIBLE
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

        save_address.setOnClickListener {
            if (fullname_section.text?.isEmpty()!!) {
                req_field9.visibility = View.VISIBLE
                fullname_section.error = getString(R.string.error)
            } else {
                req_field9.visibility = View.INVISIBLE
                fullname_section.error = null
            }
            if (postal_section.text?.isEmpty()!!) {
                req_field10.visibility = View.VISIBLE
                postal_section.error = getString(R.string.error)
            } else {
                req_field10.visibility = View.INVISIBLE
                postal_section.error = null
            }
            if (house_number_section.text?.isEmpty()!!) {
                req_field11.visibility = View.VISIBLE
                house_number_section.error = getString(R.string.error)
            } else {
                req_field11.visibility = View.INVISIBLE
                house_number_section.error = null
            }
            if (streetname_section.text?.isEmpty()!!) {
                req_field12.visibility = View.VISIBLE
                streetname_section.error = getString(R.string.error)
            } else {
                req_field12.visibility = View.INVISIBLE
                streetname_section.error = null
            }
            if (place_section.text?.isEmpty()!!) {
                req_field13.visibility = View.VISIBLE
                place_section.error = getString(R.string.error)
            } else {
                req_field13.visibility = View.INVISIBLE
                place_section.error = null
            }
            if (country_selector3.isEmpty()) req_field14.visibility = View.VISIBLE
            else req_field14.visibility = View.INVISIBLE
            if (req_field9.visibility == View.INVISIBLE && req_field10.visibility == View.INVISIBLE
                && req_field11.visibility == View.INVISIBLE && req_field12.visibility == View.INVISIBLE
                && req_field13.visibility == View.INVISIBLE && req_field14.visibility == View.INVISIBLE
            )
                findNavController().navigate(R.id.nav_details)
        }

    }
}