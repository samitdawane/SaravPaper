package com.samit.saravpaper.ui.select_paper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.samit.mytodo.ui.base.BaseRecyclerViewAdapter
import com.samit.saravpaper.R
import com.samit.saravpaper.databinding.ListrowPaperBinding
import com.samit.saravpaper.models.Paper

class PaperAdapter : BaseRecyclerViewAdapter<Paper, ListrowPaperBinding>() {

    override fun getItemViewBinding(inflater: LayoutInflater): ListrowPaperBinding =
        ListrowPaperBinding.inflate(inflater)

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ListrowPaperBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        holder.binding.llRow.setLayoutParams(lp)
        items?.let {

            if (it[position].paper_eng.equals("Maths")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_maths)
            } else if (it[position].paper_eng.equals("English")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_english)
            } else if (it[position].paper_eng.equals("Marathi")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_marathi)
            }else if (it[position].paper_eng.equals("Samanya dnyan")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_gk)
            }else if (it[position].paper_eng.equals("Gani")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_mar_gani)
            }else if (it[position].paper_eng.equals("Poem")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_poem)
            } else if (it[position].paper_eng.equals("Goshti")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_story_mar)
            }else if (it[position].paper_eng.equals("Story")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_story)
            }else if (it[position].paper_eng.equals("Ganit")) {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_maths)
            }else {
                holder.binding.ivSubject.setImageResource(R.drawable.ic_marathi)
            }

            if (it[position].paper_eng.equals("Maths") || it[position].paper_eng.equals("English")
                || it[position].paper_eng.equals("GK")
            ) {
                holder.binding.txtPaper.text = it[position].paper_eng
                holder.binding.txtTerm.text = it[position].testType_eng
            } else {
                holder.binding.txtPaper.text = it[position].paper_mar
                holder.binding.txtTerm.text = it[position].testType_mar
            }



            holder.binding.llPaper.setOnClickListener {
                recyclerViewItemClickListener?.invoke(position, items!![position], it)
            }


        }
    }


}