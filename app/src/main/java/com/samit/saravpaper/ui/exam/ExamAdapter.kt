package com.samit.saravpaper.ui.exam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.samit.mytodo.ui.base.BaseRecyclerViewAdapter
import com.samit.saravpaper.R
import com.samit.saravpaper.databinding.ListrowExamBinding
import com.samit.saravpaper.models.Answer

class ExamAdapter : BaseRecyclerViewAdapter<Answer, ListrowExamBinding>() {

    override fun getItemViewBinding(inflater: LayoutInflater): ListrowExamBinding =
        ListrowExamBinding.inflate(inflater)

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ListrowExamBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        holder.binding.llRow.setLayoutParams(lp)
        items?.let {

            if(it[position].isSelected){
                holder.binding.rbAns.isChecked= true
                holder.binding.rlBox.setBackgroundDrawable(context.getDrawable(R.drawable.rounded_corner_purple_border))
            }else{
                holder.binding.rbAns.isChecked = false
                holder.binding.rlBox.setBackgroundDrawable(context.getDrawable(R.drawable.rounded_corner_gray_border))
            }
            holder.binding.txtAns.text = it[position].ans
            if(it[position].ansImg == null || it[position].ansImg?.length==0){
                holder.binding.ivAnsImage.visibility =  View.GONE
            }else{
                holder.binding.ivAnsImage.visibility =  View.VISIBLE
                Glide.with(context).load(it[position].ansImg).override(150, 150).into(holder.binding.ivAnsImage)
            }

            holder.binding.rlBox.setOnClickListener {
                recyclerViewItemClickListener?.invoke(position, items!![position], it)
            }




        }
    }


}