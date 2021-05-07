package com.example.sneakersalert.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.sneakersalert.R
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import kotlinx.android.synthetic.main.fragment_request_sneaker.*


class RequestSneakerFragment : Fragment(R.layout.fragment_request_sneaker) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_sneaker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sizes = arrayListOf(36, 37, 38, 39, 40, 41, 42, 43, 44, 45)
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

    }


}