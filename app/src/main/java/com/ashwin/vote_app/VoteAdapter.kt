package com.ashwin.vote_app

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class VoteAdapter(context: Context, array: ArrayList<VoteModel>):RecyclerView.Adapter<VoteAdapter.Viewholder>() {

    private var VoteModelList: ArrayList<VoteModel> = array

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.vote_card_view, parent, false)
        Log.w(this.toString(), "onCreateViewHolder")
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Log.w(this.toString(), "onBindViewHolder")
        val model:VoteModel = VoteModelList[position]
        holder.vote_desc.text = model.getTitle()
        holder.vote_title.text = model.getDesc()
    }

    override fun getItemCount(): Int {
        Log.v(this.toString(), "getItemCount")
        return VoteModelList.size
    }


    class Viewholder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val vote_title:TextView = itemView.findViewById(R.id.vote_title)
        val vote_desc:TextView = itemView.findViewById(R.id.vote_desc)
    }

}
