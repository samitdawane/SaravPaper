package com.samit.saravpaper.ui.select_standard

import androidx.lifecycle.ViewModel

class SelectStanardViewModel(private val repository: SelectStandardRepository): ViewModel() {

    fun getStandardList() = repository.getStandardList()

}