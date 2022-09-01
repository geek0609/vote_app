package com.ashwin.vote_app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class updateInfo : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    private var database = FirebaseDatabase.getInstance("https://voteapp-1e334-default-rtdb.asia-southeast1.firebasedatabase.app")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_info)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val update_button: Button = findViewById(R.id.updateInfoButton)
        var noIssues:Boolean = true
        var alreadyVoted = "0"
        var name_prev: String
        var class_prev: String
        var sec_prev: String
        val grade_entered: EditText = findViewById<EditText>(R.id.grade)
        val name_entered: EditText= findViewById<EditText>(R.id.name)
        val sec_entered: EditText = findViewById<EditText>(R.id.section)
        database.getReference("user").child(currentUser?.uid.toString()).get().addOnSuccessListener {
            val current_info = it
            name_prev = current_info.child("name").value.toString()
            if (name_prev == "null"){
                Toast.makeText(applicationContext, "New User", Toast.LENGTH_LONG).show()
            } else {
                class_prev = current_info.child("grade").value.toString()
                sec_prev = current_info.child("section").value.toString()
                alreadyVoted = current_info.child("voted").value.toString()
                name_entered.setText(name_prev)
                grade_entered.setText(class_prev)
                sec_entered.setText(sec_prev)
                if (alreadyVoted == "1") {
                    val intent_alreadyvoted = Intent(this, AlreadyVoted::class.java)
                    startActivity(intent_alreadyvoted)
                }
            }

        }.addOnFailureListener{
            Log.i("firebase", "Error getting data", it)
        }

        update_button.setOnClickListener{
            val warningred = getColor(R.color.WarningRed)
            val primary_color = getColor(R.color.colorPrimary)
            val name = name_entered.text.toString()
            val entergrade: TextView =  findViewById<TextView>(R.id.entergrade)
            val grade:Int = grade_entered.text.toString().toInt()
            val sec = sec_entered.text.toString()
            Toast.makeText(this@updateInfo, "hmm sus", Toast.LENGTH_LONG).show()

            database.getReference("user").child(currentUser?.uid.toString()).get().addOnSuccessListener {
                val current_info = it
                name_prev = current_info.child("name").value.toString()
                if (name_prev == "null") {
                    Toast.makeText(applicationContext, "New User", Toast.LENGTH_LONG).show()
                } else {
                    class_prev = current_info.child("grade").value.toString()
                    sec_prev = current_info.child("section").value.toString()
                    alreadyVoted = current_info.child("voted").value.toString()
                    name_entered.setText(name_prev)
                    grade_entered.setText(class_prev)
                    sec_entered.setText(sec_prev)
                    if (alreadyVoted == "1") {
                        val intent_alreadyvoted = Intent(this, AlreadyVoted::class.java)
                        startActivity(intent_alreadyvoted)
                    }
                }


                noIssues = if (grade > 12) {
                    entergrade.setTextColor(warningred)
                    false
                } else {
                    entergrade.setTextColor(primary_color)
                    true
                }
                val email: String = currentUser?.email.toString()
                val id: String = currentUser?.uid.toString()
                if (noIssues) {
                    uploadInfo(email, name, grade, sec, id, alreadyVoted)
                    val intent = Intent(this, VoteScreen::class.java)
                    startActivity(intent)
                }

                if (alreadyVoted == "1") {
                    Toast.makeText(this@updateInfo, "Already Voted", Toast.LENGTH_LONG).show()
                    update_button.isClickable = false
                }
            }
        }
    }
    override fun onBackPressed() {
        finish()
    }

    fun uploadInfo(email:String, name:String, grade:Int, section:String, id:String, alreadyVoted:String){
        Toast.makeText(applicationContext, getString(R.string.SignInToast)+ "TESTMODE:EMAIL HIDDEN", Toast.LENGTH_LONG).show()
        Toast.makeText(this@updateInfo, "hmm very sus", Toast.LENGTH_LONG).show()
        val DBRef = database.getReference("user")
        val user = User(email, name, grade, section, alreadyVoted)
        DBRef.child(id).setValue(user)
    }
}

@IgnoreExtraProperties
data class User(val email: String, val name: String, val grade: Int, val section: String, val voted:String) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
