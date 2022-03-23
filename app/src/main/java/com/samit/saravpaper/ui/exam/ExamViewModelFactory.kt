package com.samit.saravpaper.ui.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExamViewModelFactory : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ExamRepository::class.java)
            .newInstance(ExamRepository())
    }


}