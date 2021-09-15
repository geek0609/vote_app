package com.ashwin.vote_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class VoteScreen:AppCompatActivity(), ItemClickListener {

    private lateinit var VoteRV: RecyclerView
    private lateinit var voteModelArrayList: ArrayList<VoteModel>
    private var database =
        FirebaseDatabase.getInstance("https://voteapp-1e334-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var mAuth: FirebaseAuth
    var isconfirmed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vote_screen)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        Log.v(this.toString(), "VoteScreen Set")
        VoteRV = findViewById(R.id.VoteRecycler)
        Log.v(this.toString(), "RecyclerView Set")
        voteModelArrayList = ArrayList()

        database.getReference("candidate").get().addOnSuccessListener {
            val getValue = it
            for (i in getValue.children) {
                voteModelArrayList.add(
                    VoteModel(
                        i.child("name").value.toString(),
                        i.child("desc").value.toString(),
                        i.key.toString(),
                    )
                )
            }
            val adepter = VoteAdapter(this, voteModelArrayList, this@VoteScreen)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            VoteRV.layoutManager = linearLayoutManager
            Log.v(this.toString(), "layoutManager Set")
            VoteRV.adapter = adepter
            Log.v(this.toString(), "adapter Set")
        }
    }

    lateinit var voted_id:String
    lateinit var previous_button:Button
    var tries:Int = 0
    var votes_for_cand = 0
    override fun onItemClicked(textData: VoteModel, voteButton: Button) {
        // Toast.makeText(applicationContext, textData.getTitle().toString(), Toast.LENGTH_LONG).show()
        if (isconfirmed && voted_id == textData.getID()){
            // vote success
            val currentUser = mAuth.currentUser
            database.getReference("user").child(currentUser?.uid.toString()).get().addOnSuccessListener {
                val current_info = it
                val alreadyVoted = current_info.child("voted").value.toString()

                if (alreadyVoted == "1") {
                        val intent_alreadyvoted = Intent(this, AlreadyVoted::class.java)
                        startActivity(intent_alreadyvoted)
                }else{
                database.getReference("candidate").child(textData.getID()).get().addOnSuccessListener {
                val getInfo = it
                votes_for_cand = Integer.parseInt(getInfo.child("votes").value.toString())
                votes_for_cand += 1
                database.getReference("candidate").child(textData.getID()).child("votes").setValue(votes_for_cand)
                database.getReference("user").child(mAuth.currentUser?.uid.toString()).child("voted").setValue("1")
                val intent_alreadyvoted = Intent(this, VoteOK::class.java)
                startActivity(intent_alreadyvoted)
            }
                }

            }.addOnFailureListener{
                Log.i("firebase", "Error getting data", it)
            }
        }
        else {
            val confirmation = resources.getString(R.string.vote_confirm)
            Toast.makeText(this@VoteScreen, confirmation, Toast.LENGTH_SHORT).show()
            this.isconfirmed = true
            this.voted_id = textData.getID()
            voteButton.setTextColor(getColor(R.color.SuccessGreen))
            if (tries!=0) {
                previous_button.setTextColor(getColor(R.color.colorSecondary))
            }
            this.previous_button = voteButton
            tries += 1
        }


    }
}