package com.samit.saravpaper.ui.exam

import androidx.lifecycle.ViewModel
import com.samit.saravpaper.models.Question

class ExamViewModel(private val repository: ExamRepository): ViewModel() {

    suspend fun addQuestion(papers: List<Question>) = repository.setQuestionPaper(papers)
    suspend fun getQuePapers(stdCode : String, paperCode : String) = repository.getQuestionPaper(stdCode,paperCode)

}