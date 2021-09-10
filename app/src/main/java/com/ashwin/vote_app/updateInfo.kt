package com.ashwin.vote_app

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
        var currentUser = mAuth.currentUser
        val update_button: Button = findViewById(R.id.updateInfoButton)
        var noIssues:Boolean = true

        database.getReference("user").child(currentUser?.uid.toString()).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            Toast.makeText(applicationContext, it.value.toString(), Toast.LENGTH_LONG ).show()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        update_button.setOnClickListener{
            val warningred = getColor(R.color.WarningRed)
            val primary_color = getColor(R.color.colorPrimary)

            val name = findViewById<EditText>(R.id.name).text.toString()

            val entergrade: TextView =  findViewById<TextView>(R.id.entergrade)
            val grade_entered: EditText = findViewById<EditText>(R.id.grade)
            val grade:Int = grade_entered.text.toString().toInt()
            if (grade > 12) {
                entergrade.setTextColor(warningred)
                entergrade.text = entergrade.text.toString() + " Enter value under 12"
                noIssues = false
            } else {
                entergrade.setTextColor(primary_color)
                noIssues = true

            }

            val sec = findViewById<EditText>(R.id.section).text.toString()

            val email:String = currentUser?.email.toString()

            val id:String = currentUser?.uid.toString()

            if (noIssues){
                uploadInfo(email, name, grade, sec, id)
            }


        }

    }
    override fun onBackPressed() {
        finish()
    }


    fun uploadInfo(email:String, name:String, grade:Int, section:String, id:String){
        Toast.makeText(applicationContext, getString(R.string.SignInToast)+ email, Toast.LENGTH_LONG).show()
        val DBRef = database.getReference("user")
        val user = User(email, name, grade, section)
        DBRef.child(id).setValue(user)
    }


}

@IgnoreExtraProperties
data class User(val email: String, val name: String, val grade: Int, val section: String) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
