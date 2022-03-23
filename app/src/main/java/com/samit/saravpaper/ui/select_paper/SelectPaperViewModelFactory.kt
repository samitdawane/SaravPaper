package com.samit.saravpaper.ui.select_paper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectPaperViewModelFactory : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SelectPaperRepository::class.java)
            .newInstance(SelectPaperRepository())
    }


}