package com.samit.saravpaper.ui.select_paper

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samit.saravpaper.models.Paper
import com.samit.saravpaper.utility.Constants
import com.samit.saravpaper.utility.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SelectPaperRepository {

    private val mPaperCollection = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PAPER_MASTER)

    suspend fun setPapers(mPaperList: List<Paper>) = flow<State<String>> {
        emit(State.loading())
        var db = FirebaseFirestore.getInstance()
        var result = db.runTransaction {
            for (mPaper in mPaperList) {
                mPaperCollection.add(mPaper)
            }
            Log.d(">>>>", "last line in method")
            true
        }.await()
        if (result) {
            emit(State.successMessage("Suuuuuuuuc"))
        }

    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getPaperList(stdCode : String) = flow<State<List<Paper>>> {
        emit(State.loading())
        val paperSnap = mPaperCollection.whereEqualTo("stdCode",stdCode).get().await()
        val transactions = paperSnap.toObjects(Paper::class.java)
        emit(State.success(transactions))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}