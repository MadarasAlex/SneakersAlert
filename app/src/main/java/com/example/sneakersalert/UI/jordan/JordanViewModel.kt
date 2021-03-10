package com.example.sneakersalert.UI.jordan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JordanViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {

    }
    val text: LiveData<String> = _text
}