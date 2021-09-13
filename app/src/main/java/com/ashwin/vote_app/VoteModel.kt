package com.ashwin.vote_app

class VoteModel(var vote_title: String, var vote_desc: String) {

    fun getTitle(): String {
        return vote_title
    }

    fun getDesc(): String {
        return vote_desc
    }

    fun setTitle(VoteTitle:String){
        this.vote_title = VoteTitle
    }

    fun setDesc(VoteDesc:String){
        this.vote_desc = VoteDesc
    }
}