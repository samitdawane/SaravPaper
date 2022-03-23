package com.samit.saravpaper.ui.select_paper

import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.samit.mytodo.ui.base.BaseFragment
import com.samit.saravpaper.R
import com.samit.saravpaper.databinding.FragmentSelectPaperBinding
import com.samit.saravpaper.models.Paper
import com.samit.saravpaper.utility.State
import com.samit.saravpaper.utility.hide
import com.samit.saravpaper.utility.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SelectPaperFragment :
    BaseFragment<FragmentSelectPaperBinding, SelectPaperViewModel, SelectPaperViewModelFactory>() {
    override fun getFragmentView() = R.layout.fragment_select_paper
    override fun getViewModel() = SelectPaperViewModel::class.java
    override fun getViewModelFactory() = SelectPaperViewModelFactory()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    var paperList: MutableList<Paper> = ArrayList()
    private val paperAdapter by lazy {
        PaperAdapter()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*uiScope.launch {

           // paperList.add(Paper("2","1","Second Semister","द्वितीय संत्रांत ","Marathi","मराठी "))
            //paperList.add(Paper("2","2","Second Semister","द्वितीय संत्रांत ","English","इंग्रजी "))
            //paperList.add(Paper("2","3","Second Semister","द्वितीय संत्रांत ","Maths","मॅथ्स "))
            //paperList.add(Paper("2","5","Second Semister","द्वितीय संत्रांत ","Samanya dnyan","सामान्य ज्ञान"))
            addPapers(paperList)
        }*/


       /* binding.llMarathi.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_selectPaperFragment_to_examFragment)
        }*/

        paperAdapter.recyclerViewItemClickListener ={position,item,view ->
            if(view.id == R.id.llPaper){
                val bundle = Bundle()
                bundle.putSerializable("PAPER", item);
                Navigation.findNavController(view).navigate(R.id.action_selectPaperFragment_to_examFragment,bundle)
            }
        }
        uiScope.launch {
            loadPapers("2")
        }
    }

    private suspend fun loadPapers(stdCode : String) {
        viewModel.getPapers(stdCode).collect{ state ->

            when (state) {
                is State.Loading -> {
                    binding.progressBar.show()
                }

                is State.Success -> {
                    Log.d(">>", ">>allpaperList>>" +state.data.size)
                    binding.progressBar.hide()
                    populatePaperData(state.data)
                }

                is State.Failed -> {
                    binding.progressBar.hide()
                }
            }
        }

    }

    private fun populatePaperData(data: List<Paper>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPapers.layoutManager = layoutManager
        binding.rvPapers.adapter = paperAdapter
        if(data.isNotEmpty()){
            paperAdapter.items = data
        }
    }

    private suspend fun addPapers(papers: List<Paper>) {
        Log.d(">>>", ">>>>>Posted")
        viewModel.addPapers(papers).collect { state ->
            when (state) {
                is State.Loading -> {
                    Log.e(">>","inside loadding")
                }
                is State.Success -> {
                    Log.e(">>","inside Success")
                }
                is State.Failed -> {
                    Log.e(">>","inside Failed")
                }
            }
        }

    }

}