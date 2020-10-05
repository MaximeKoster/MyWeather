package com.example.whatstheweather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Page de recherche"
    }

    val text: LiveData<String> = _text

    private val _searchText = MutableLiveData<String>();
    val searchText: LiveData<String> = _searchText;

}