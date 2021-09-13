package com.ashwin.vote_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VoteScreen:AppCompatActivity(){

    private lateinit var VoteRV:RecyclerView
    private lateinit var voteModelArrayList:ArrayList<VoteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vote_screen)
        Log.v(this.toString(), "VoteScreen Set")
        VoteRV = findViewById(R.id.VoteRecycler)
        Log.v(this.toString(), "RecyclerView Set")
        voteModelArrayList = ArrayList()
        voteModelArrayList.add(VoteModel("Vote_Title1", "Vote_Desc1"))
        voteModelArrayList.add(VoteModel("Vote_Title2", "Vote_Desc2"))
        voteModelArrayList.add(VoteModel("Vote_Title3", "Vote_Desc3"))
        voteModelArrayList.add(VoteModel("Vote_Title4", "Vote_Desc4"))
        voteModelArrayList.add(VoteModel("Vote_Title5", "Vote_Desc5"))
        val adepter = VoteAdapter(this, voteModelArrayList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        VoteRV.layoutManager = linearLayoutManager
        Log.v(this.toString(), "layoutManager Set")
        VoteRV.adapter = adepter
        Log.v(this.toString(), "adapter Set")
    }
}