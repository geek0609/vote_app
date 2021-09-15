package com.ashwin.vote_app

import android.widget.Button

interface ItemClickListener {
    fun onItemClicked(textData:VoteModel, voteButton: Button)
}