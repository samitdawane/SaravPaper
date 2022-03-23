package com.samit.saravpaper.ui.exam

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.samit.mytodo.ui.base.BaseFragment
import com.samit.saravpaper.R
import com.samit.saravpaper.databinding.FragmentExamBinding
import com.samit.saravpaper.models.Answer
import com.samit.saravpaper.models.Paper
import com.samit.saravpaper.models.Question
import com.samit.saravpaper.utility.State
import com.samit.saravpaper.utility.hide
import com.samit.saravpaper.utility.jetpack_datastore.UserDataStore
import com.samit.saravpaper.utility.show
import com.samit.saravpaper.utility.snackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

class ExamFragment :
    BaseFragment<FragmentExamBinding, ExamViewModel, ExamViewModelFactory>() {
    override fun getFragmentView() = R.layout.fragment_exam
    override fun getViewModel() = ExamViewModel::class.java
    override fun getViewModelFactory() = ExamViewModelFactory()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val examAdapter by lazy {
        ExamAdapter()
    }
    var ansList = ArrayList<Answer>()
    var quePosition = 0
    var questionDataList: MutableList<Question> = ArrayList()
    lateinit var paperData: Paper
    private lateinit var userPreferences: UserDataStore
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //addQuestionsJSON()


        initView()
        initListner()
    }



    private fun initListner() {
        binding.ivProfile.setOnClickListener {

            Navigation.findNavController(requireView()).navigate(R.id.action_examFragment_to_selectStandardFragment)
        }

        binding.txtPrevious.setOnClickListener {
            quePosition--
            populateQPaperData()
        }

        binding.txtNext.setOnClickListener {
            quePosition++
            populateQPaperData()
        }

        binding.txtSubmit.setOnClickListener {
            setButtonVisibility()
            var isAnsSelected = false;
            var selectedAnsPos = -1;
            ansList.forEachIndexed { index, answer ->
                if(answer.isSelected){
                    isAnsSelected = true
                    selectedAnsPos = index;
                }
            }
            if(isAnsSelected){
                selectedAnsPos = selectedAnsPos+1
                if (selectedAnsPos == correctAnsPos){
                    binding.ivRightAns.visibility =  View.VISIBLE
                    Handler().postDelayed({
                        binding.ivRightAns.visibility =  View.GONE
                    }, 1500)
                }else{
                    binding.ivWrongAns.visibility =  View.VISIBLE
                    Handler().postDelayed({
                        binding.ivWrongAns.visibility =  View.GONE
                    },1500)

                }

            }else{
                binding.rootLayout.snackBar("उत्तर निवडा")
            }



        }

        examAdapter.recyclerViewItemClickListener ={position,item,view ->
            if(view.id == R.id.rlBox){

                ansList.forEachIndexed { index, answer ->
                    if(position == index){
                        answer.isSelected = true
                    }else{
                        answer.isSelected = false
                    }
                    ansList.set(index, answer)
                }
                examAdapter.items = ansList
            }
        }
    }

    private fun setButtonVisibility() {

        if(quePosition ==0){
            binding.txtPrevious.visibility =  View.INVISIBLE
        }else{
            binding.txtPrevious.visibility =  View.VISIBLE
        }

        if(quePosition == questionDataList.size -1){
            binding.txtNext.visibility =  View.INVISIBLE
        }else{
            binding.txtNext.visibility =  View.VISIBLE
        }
    }

    private fun initView() {
        userPreferences = UserDataStore(requireActivity())
        arguments?.let {
            paperData = requireArguments().getSerializable("PAPER") as Paper
            if (paperData.paper_eng.equals("Maths") || paperData.paper_eng.equals("English")
                || paperData.paper_eng.equals("GK")
            ) {
                binding.txtSubmit.text = "Submit"
                binding.txtNext.text = "Next"
                binding.txtPrevious.text = "Previous"
                binding.txtHeading.setText(paperData.paper_eng)
                binding.txtTerm.setText(paperData.testType_eng)
                userPreferences.standard.asLiveData().observe(this.viewLifecycleOwner, Observer {
                    it?.let {
                        if(it!!.length > 0){
                            binding.txtStandard.setText(it)
                        }
                    }
                })


            } else {
                binding.txtSubmit.text = "समाविष्ट करा"
                binding.txtNext.text = "पुढे"
                binding.txtPrevious.text = "मागे"
                binding.txtHeading.setText(paperData.paper_mar)
                binding.txtTerm.setText(paperData.testType_mar)
                userPreferences.standardMar.asLiveData().observe(this.viewLifecycleOwner, Observer {
                    it?.let {
                        if(it!!.length > 0){
                            binding.txtStandard.setText(it)
                        }
                    }
                })
            }


        }


        Glide.with(this).load(R.drawable.very_good).into(binding.ivRightAns)
        binding.ivRightAns.visibility =  View.GONE

        Glide.with(this).load(R.drawable.wrong).into(binding.ivWrongAns)
        binding.ivWrongAns.visibility =  View.GONE

        uiScope.launch {
            loadQuestion(paperData.stdCode!!,paperData.paperCode!!)
        }
    }

    private fun addQuestionsJSON() {
        //var str = activity?.readJsonAsset("motha_shishu_que_marathi.json");
        //var str = activity?.readJsonAsset("motha_shishu_que_english.json");
        var str = activity?.readJsonAsset("motha_shishu_que_samanya_dnyan.json");
        val gson = Gson()
        val queList = gson.fromJson(str, Array<Question>::class.java).toList()
        Log.e(">>>>","json>objectList>>"+queList.size)
         uiScope.launch {
             addQuestion(queList)
       }
    }

    private suspend fun loadQuestion(stdCode : String, paperCode : String) {
        viewModel.getQuePapers(stdCode,paperCode).collect{ state ->

            when (state) {
                is State.Loading -> {
                    binding.progressBar.show()
                }

                is State.Success -> {
                    Log.d(">>", ">>allpaperList>>" +state.data.size)
                    binding.progressBar.hide()
                    var mList = state.data as MutableList<Question>
                    if(mList.isNotEmpty()){
                        binding.llContent.visibility =  View.VISIBLE
                        binding.llActionButtons.visibility =  View.VISIBLE
                        binding.txtNoDataFound.visibility =  View.GONE
                        val sortedList = mList.sortedWith(compareBy({ it.qMainOrder }, { it.queOrder }))
                        questionDataList  = sortedList as MutableList<Question>
                        populateQPaperData()
                    }else{
                        binding.txtNoDataFound.visibility =  View.VISIBLE
                        /*binding.llContent.visibility =  View.GONE
                        binding.llActionButtons.visibility =  View.GONE*/

                    }

                }

                is State.Failed -> {
                    binding.progressBar.hide()
                }
            }
        }

    }

    private fun populateQPaperData() {
        setButtonVisibility()
        var que = questionDataList.get(quePosition);
        setAnsList(que)

    }

    var correctAnsPos = -1
    private fun setAnsList(data: Question) {
        ansList.clear()
        correctAnsPos = data.correctANS
        if(data.ans1 != null || data.ans1_Img != null){
            ansList.add(Answer(data.ans1,data.ans1_Img,false))
        }
        if(data.ans2 != null || data.ans2_Img != null){
            ansList.add(Answer(data.ans2,data.ans2_Img,false))
        }
        if(data.ans3 != null || data.ans3_Img != null){
            ansList.add(Answer(data.ans3,data.ans3_Img,false))
        }
        if(data.ans4 != null || data.ans4_Img != null){
            ansList.add(Answer(data.ans4,data.ans4_Img,false))
        }

        Log.d(">>>", ">>>>>ansList>>>"+ansList.size)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvAnswer.layoutManager = layoutManager
        binding.rvAnswer.adapter = examAdapter
        if(ansList.isNotEmpty()){
            examAdapter.items = ansList
        }

        binding.txtQuesMain.text = "प्र."+ data.qMainOrder+" " +data.qMain
        var qq =  ""
        if(data.que != null){
            qq = ""+data.que
        }

        binding.txtQues.text =""+data.queOrder +"). "+qq
        if(data.queImg != null){
            binding.ivQues.visibility =  View.VISIBLE
            Glide.with(requireActivity()).load(data.queImg).override(150, 150).into(binding.ivQues)
        }else{
            binding.ivQues.visibility =  View.GONE
        }
        var isQImgAvail = false
        var isQAvail = false
        if(data.queImg != null ){
            isQImgAvail =  true
        }
        if(data.que != null ||data.que?.length != 0){
            isQAvail =  true
        }
        if(isQImgAvail|| isQAvail){
            binding.llQues.visibility =  View.VISIBLE
        }else{
            binding.llQues.visibility =  View.GONE
        }


    }

    private suspend fun addQuestion(papers: List<Question>) {
        Log.d(">>>", ">>>>>Posted")
        viewModel.addQuestion(papers).collect { state ->
            when (state) {
                is State.Loading -> {
                    Log.e(">>","inside loadding")
                }
                is State.SuccessMsg -> {
                    Log.e(">>","inside Success")
                }
                is State.Failed -> {
                    Log.e(">>","inside Failed")
                }
            }
        }

    }

    @Throws(IOException::class)
    fun Context.readJsonAsset(fileName: String): String {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

}