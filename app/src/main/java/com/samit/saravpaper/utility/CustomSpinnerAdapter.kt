package com.samit.saravpaper.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.samit.saravpaper.R

import com.samit.saravpaper.models.SpinnerData


class CustomSpinnerAdapter(ctx: Context, countries: ArrayList<SpinnerData>) :
    ArrayAdapter<SpinnerData>(ctx, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    fun createItemView(position: Int, recycledView: View?, parent: ViewGroup):View {
        val spinnerData = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.listrow_spinner,
            parent,
            false
        )
        val txtSpnrValue = view.findViewById<TextView>(R.id.txtSpnrValue)

        spinnerData?.let {
            //view.imageViewFlag.setImageResource(spinnerData.flag)
            txtSpnrValue.text = spinnerData.value1
        }
        return view
    }
}