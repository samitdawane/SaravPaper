package com.samit.saravpaper.ui.select_standard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectStandardViewModelFactory : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SelectStandardRepository::class.java)
            .newInstance(SelectStandardRepository())
    }


}