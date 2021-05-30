package com.example.sneakersalert.ui.request

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.sneakersalert.R
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import kotlinx.android.synthetic.main.fragment_request_sneaker.*


class RequestSneakerFragment : Fragment(R.layout.fragment_request_sneaker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sizes = arrayListOf(
            35,
            35.5,
            36,
            36.5,
            37,
            37.5,
            38,
            38.5,
            39,
            39.5,
            40,
            40.5,
            41,
            41.5,
            42,
            42.5,
            43,
            43.5,
            44,
            44.5,
            45,
            45.5,
            46
        )
        val adapter = ArrayAdapter(
            this.requireActivity(),
            android.R.layout.simple_list_item_1, sizes
        )
        eu_size.threshold = 0
        eu_size.setAdapter(adapter)
        drop_sizes.setOnClickListener {
            eu_size.showDropDown()
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
                    //         req_field9.visibility = View.VISIBLE
                    name_section2.error = getString(R.string.error)
                } else {
//                    req_field9.visibility=View.INVISIBLE
                    name_section2.error = null
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
                    last_name_section2.error = getString(R.string.error)
                } else {
                    //      req_field10.visibility=View.INVISIBLE
                    last_name_section2.error = null
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
                    email_section2.error = getString(R.string.error)
                } else {
                    //    req_field11.visibility = View.INVISIBLE
                    email_section2.error = null
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
                    brand_name_section.error = getString(R.string.error)
                } else {
                    //     req_field12.visibility=View.INVISIBLE
                    brand_name_section.error = null
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
                    model_name_section.error = getString(R.string.error)
                    //      req_field13.visibility = View.VISIBLE
                } else {
                    //      req_field13.visibility = View.INVISIBLE
                    model_name_section.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        name_section2.addTextChangedListener(fullnameWatcher)
        last_name_section2.addTextChangedListener(postalWatcher)
        email_section2.addTextChangedListener(houseWatcher)
        brand_name_section.addTextChangedListener(streetnameWatcher)
        model_name_section.addTextChangedListener(placeWatcher)
        request_sneaker_button.setOnClickListener {
            if (name_section2.text?.isEmpty()!!) {
                //   req_field9.visibility=View.VISIBLE
                name_section2.error = getString(R.string.error)
            } else {
                //   req_field9.visibility=View.INVISIBLE
                name_section2.error = null
            }
            if (last_name_section2.text?.isEmpty()!!) {
                //   req_field10.visibility=View.VISIBLE
                last_name_section2.error = getString(R.string.error)
            } else {
                //  req_field10.visibility=View.INVISIBLE
                last_name_section2.error = null
            }
            if (email_section2.text?.isEmpty()!!) {
                //    req_field11.visibility=View.VISIBLE
                email_section2.error = getString(R.string.error)
            } else {
                //  req_field11.visibility=View.INVISIBLE
                email_section2.error = null
            }
            if (brand_name_section.text?.isEmpty()!!) {
                //      req_field13.visibility=View.VISIBLE
                brand_name_section.error = getString(R.string.error)
            } else {
                //     req_field13.visibility=View.INVISIBLE
                brand_name_section.error = null
            }
            if (model_name_section.text?.isEmpty()!!) {
                //      req_field13.visibility=View.VISIBLE
                model_name_section.error = getString(R.string.error)
            } else {
                //     req_field13.visibility=View.INVISIBLE
                model_name_section.error = null
            }
            if (want_to_pay_section.text?.isEmpty()!!) {
                //      req_field13.visibility=View.VISIBLE
                want_to_pay_section.error = getString(R.string.error)
            } else {
                //     req_field13.visibility=View.INVISIBLE
                want_to_pay_section.error = null
            }

        }
    }


}