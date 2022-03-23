package com.samit.saravpaper.ui.exam

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samit.saravpaper.models.Question
import com.samit.saravpaper.utility.Constants
import com.samit.saravpaper.utility.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class ExamRepository {

    private val mQuestionCollection = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_QUESTION_MASTER)

    suspend fun setQuestionPaper(mPaperList: List<Question>) = flow<State<String>> {
        emit(State.loading())
        var db = FirebaseFirestore.getInstance()
        var result = db.runTransaction {
            for (mPaper in mPaperList) {
                mQuestionCollection.add(mPaper)
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


    fun getQuestionPaper(stdCode : String,paperCode : String) = flow<State<List<Question>>> {
        emit(State.loading())
        val paperSnap = mQuestionCollection.
        whereEqualTo("stdCode",stdCode).whereEqualTo("paperCode",paperCode)
            //.orderBy("qMainOrder")
            .get().await()
        val queList = paperSnap.toObjects(Question::class.java)
        emit(State.success(queList))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}