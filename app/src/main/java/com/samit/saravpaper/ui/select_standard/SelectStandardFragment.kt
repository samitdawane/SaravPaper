package com.samit.saravpaper.ui.select_standard


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.samit.mytodo.ui.base.BaseFragment
import com.samit.saravpaper.R
import com.samit.saravpaper.databinding.FragmentSelectStandardBinding
import com.samit.saravpaper.models.SpinnerData
import com.samit.saravpaper.models.Standard
import com.samit.saravpaper.utility.CustomSpinnerAdapter
import com.samit.saravpaper.utility.State
import com.samit.saravpaper.utility.hide
import com.samit.saravpaper.utility.jetpack_datastore.UserDataStore
import com.samit.saravpaper.utility.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SelectStandardFragment :
    BaseFragment<FragmentSelectStandardBinding, SelectStanardViewModel, SelectStandardViewModelFactory>() {
    override fun getFragmentView() = R.layout.fragment_select_standard
    override fun getViewModel() = SelectStanardViewModel::class.java
    override fun getViewModelFactory() = SelectStandardViewModelFactory()
    lateinit var allStatndardList: List<Standard>
    private lateinit var userPreferences: UserDataStore
    var stdDataList: MutableList<SpinnerData> = ArrayList()
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userPreferences = UserDataStore(requireActivity())
        uiScope.launch {
            loadAllStatndardData()
        }
        binding.txtSelectStandard.setOnClickListener {
            binding.spnrStandard.performClick()
        }

        binding.txtNext.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_selectStandardFragment_to_selectPaperFragment)
        }

    }

    private suspend fun loadAllStatndardData() {
        viewModel.getStandardList().collect{ state ->

            when (state) {
                is State.Loading -> {
                     binding.progressBar.show()
                }

                is State.Success -> {
                    Log.d(">>", ">>allShareDataList>>" +state.data.size)
                    binding.progressBar.hide()
                    stdDataList.add(SpinnerData("-1","इयत्ता निवडा","Select Standard"))
                    for (mData in state.data){
                        stdDataList.add(SpinnerData(mData.stdCode,mData.std_mar,mData.std_eng))
                    }
                    populateSpinner(stdDataList)
                }

                is State.Failed -> {
                    binding.progressBar.hide()
                }
            }
        }

    }

    private fun populateSpinner(data: List<SpinnerData>) {
        val adapter1 = CustomSpinnerAdapter(requireActivity(),
            data as ArrayList<SpinnerData>
        )
        binding.spnrStandard.adapter = adapter1


        binding.spnrStandard.onItemSelectedListener  =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                binding.txtSelectStandard.text = data.get(position).value1
                if (position>0){
                    lifecycleScope.launch {
                        userPreferences.setStandard(data.get(position).value2!!)
                        userPreferences.setStandardMar(data.get(position).value1!!)
                        userPreferences.setStandardCode(data.get(position).code!!)
                    }

                }
            }

        }
    }


}


