package com.samit.saravpaper.ui.select_paper

import androidx.lifecycle.ViewModel
import com.samit.saravpaper.models.Paper

class SelectPaperViewModel(private val repository: SelectPaperRepository): ViewModel() {
    suspend fun addPapers(papers: List<Paper>) = repository.setPapers(papers)
    suspend fun getPapers(stdCode : String) = repository.getPaperList(stdCode)

}