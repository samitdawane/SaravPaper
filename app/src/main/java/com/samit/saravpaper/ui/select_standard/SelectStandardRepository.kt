package com.samit.saravpaper.ui.select_standard

import com.google.firebase.firestore.FirebaseFirestore
import com.samit.saravpaper.models.Standard
import com.samit.saravpaper.utility.Constants
import com.samit.saravpaper.utility.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SelectStandardRepository {

    private val mStandardCollection = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_STANDARD_MASTER)

    fun getStandardList() = flow<State<List<Standard>>> {
        emit(State.loading())
        val standardSnap = mStandardCollection.get().await()
        val standardList = standardSnap.toObjects(Standard::class.java)
        emit(State.success(standardList))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)



}