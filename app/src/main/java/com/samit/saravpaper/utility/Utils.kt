package com.samit.saravpaper.utility;

import android.annotation.SuppressLint
import android.os.Build
import java.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {


    @RequiresApi(Build.VERSION_CODES.O)
    fun getStringToBase64(str : String) : String{
        return Base64.getEncoder().encodeToString(str.toByteArray())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBase64ToString(str : String) : String{
        val decodedBytes = Base64.getDecoder().decode(str)
        return String(decodedBytes)
    }


    fun getCurrentDate(dateFormat : String) : String{
        val sdf = SimpleDateFormat(dateFormat)
        val currentDate = sdf.format(Date())

        return currentDate
    }

    fun convertStringToTimestamp(str : String) : Timestamp? {
        lateinit var timeStamp : Timestamp
        try {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val mDate = formatter.parse(str) // this never ends while debugging
            Log.e("mDate", mDate.toString())
            timeStamp = Timestamp(mDate)
        }catch (e: Exception){
            Log.e("mDate",e.toString()) // this never gets called either
        }
        return timeStamp
    }


    @SuppressLint("NewApi")
    fun convertStringToDate(str : String) : String? {
        lateinit var strDate : String
        try {
            val parsedDate = LocalDate.parse(str, DateTimeFormatter.ISO_DATE)
            strDate = parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        }catch (e: Exception){
            Log.e("mDate",e.toString()) // this never gets called either
        }
        return strDate
    }

    fun dateToStringFormat(date: Date?, outputFormat: String) = with(date ?: Date()) {
        SimpleDateFormat(outputFormat).format(this)
    }

    fun getDateRangeforReport(reportType : String, date : String): Pair<String?, String?> {

        var start : String? = null
        var end : String? = null
        val cal = Calendar.getInstance()
        var dateData = date.split("-")
        var day : Int = dateData[0].toInt()
        var month : Int = dateData[1].toInt()
        var year : Int = dateData[2].toInt()

        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.YEAR,year);

        when(reportType){
            "D"->{
                val monthFirstDay: Date = cal.getTime()
                val df = SimpleDateFormat("dd-MM-yyyy")
                start = df.format(monthFirstDay)
                end = df.format(monthFirstDay)
            }
            "W"->{

                while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
                    cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
                }
                val df = SimpleDateFormat("dd-MM-yyyy")
                val FirstDayOfWeek: Date = cal.getTime()
                start = df.format(FirstDayOfWeek)

                cal.set(Calendar.DAY_OF_WEEK, 7)
                val lastDayOfWeek: Date = cal.getTime()
                end = df.format(lastDayOfWeek)
            }
            "M"->{
                cal.add(Calendar.MONTH, 0)
                cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
                val monthFirstDay: Date = cal.getTime()
                cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                val monthLastDay: Date = cal.getTime()
                val df = SimpleDateFormat("dd-MM-yyyy")
                start = df.format(monthFirstDay)
                end = df.format(monthLastDay)

            }
        }
    return Pair(start!!,end!!)

    }

    fun getDateRange(reportType : String): Pair<String?, String?> {

        var start : String? = null
        var end : String? = null
        val cal = Calendar.getInstance()

        val curDay: Date = cal.getTime()
        val df = SimpleDateFormat("dd-MM-yyyy")
        start = df.format(curDay)
        when(reportType){
            "D"->{
                end = df.format(curDay)
            }
            "W"->{

                cal.add(Calendar.DATE, -7)
                val lastDayOfWeek: Date = cal.getTime()
                end = df.format(lastDayOfWeek)


            }
            "M"->{

                cal.add(Calendar.DATE, -30)
                val lastDayOfWeek: Date = cal.getTime()
                end = df.format(lastDayOfWeek)
            }
        }
        return Pair(end!!,start!!)

    }

    fun getLastSevenDays(minusDays : Int, format : String) : String{
        val df = SimpleDateFormat(format)
        val cal = Calendar.getInstance()
        if(minusDays != 0){
            cal.add(Calendar.DATE, minusDays)
        }
        val previousDay: Date = cal.getTime()
        return df.format(previousDay)
    }

    fun getLastSevenDays(minusDays : Int, format : String, date: String) : String{
        val df = SimpleDateFormat(format)
        val cal = Calendar.getInstance()
        var dateData = date.split("-")
        var day : Int = dateData[0].toInt()
        var month : Int = dateData[1].toInt()
        var year : Int = dateData[2].toInt()

        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.YEAR,year);
        while (cal.get(Calendar.DAY_OF_WEEK) > cal.getFirstDayOfWeek()) {
            cal.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        cal.set(Calendar.DAY_OF_WEEK,minusDays)
        val previousDay: Date = cal.getTime()
        return df.format(previousDay)
    }





    fun getFormatedNumber(count: Long): String {
        val df = DecimalFormat("#.#")
        var output : String =""
        if(count >= 10000000){
            output = "${df.format(count/10000000)} Cr"
        }else if(count >= 100000){
            output = "${count/100000} Lac"
        }else if(count >= 1000){
            output = "${count/1000} K"
        }

        println("K>>>"+prettyCount(88888888L))
        return output
    }

    fun prettyCount(number: Long): String? {
        val df = DecimalFormat("#.#")
        var numberString = ""
        numberString = if (Math.abs(number / 10000000) >= 1) {
            df.format(number / 10000000.0) + "Cr"
        } else if (Math.abs(number / 100000) >= 1) {
            df.format(number / 100000.0) + "L"
        } else  /*if (Math.abs(number / 1000) >= 1)*/ {
            df.format(number / 1000.0) + "k"
        }
        return numberString
    }



}