package com.ashwin.vote_app

class VoteModel(var vote_title: String, var vote_desc: String, var cand_id: String) {
    fun getTitle(): String { return vote_title }
    fun setTitle(VoteTitle:String){ this.vote_title = VoteTitle }
    fun getDesc(): String { return vote_desc }
    fun setDesc(VoteDesc:String){ this.vote_desc = VoteDesc }
    fun getID():String{ return cand_id }
    fun setID(cand_id:String){ this.cand_id = cand_id }
}