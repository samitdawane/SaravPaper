package com.samit.saravpaper.utility;

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*



fun Context.toast(message : String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}

fun View.snackBar(message: String){
    Snackbar.make(this,message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK"){
            snackbar.dismiss()
        }
        snackbar.setActionTextColor(Color.parseColor("#BB4444"))

    }.show()
}

fun View.snackBarAct(message: String){
    Snackbar.make(this,message, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
        snackbar.setAction("OK"){
            snackbar.dismiss()
        }


    }.show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showDatePicker(et :EditText){
    var day = 0
    var month: Int = 0
    var year: Int = 0

    val calendar: Calendar = Calendar.getInstance()
    day = calendar.get(Calendar.DAY_OF_MONTH)
    month = calendar.get(Calendar.MONTH)
    year = calendar.get(Calendar.YEAR)
    val datePickerDialog =
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
            var month = "%02d".format(monthOfYear + 1)
            var day = "%02d".format(dayOfMonth)
           et.setText( Utils().convertStringToDate("$year-${month}-$day"))
           // et.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")
        }, year, month,day)
    datePickerDialog.show()
}



