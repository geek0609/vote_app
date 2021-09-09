package com.ashwin.vote_app

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class updateInfo : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_info)
        mAuth = FirebaseAuth.getInstance()

        val update_button: Button = findViewById(R.id.updateInfoButton)
        update_button.setOnClickListener{
            val warningred = getColor(R.color.WarningRed)
            val entername: TextView =  findViewById<TextView>(R.id.entername)
            entername.setTextColor(warningred)
            val entergrade: TextView =  findViewById<TextView>(R.id.entergrade)
            entergrade.setTextColor(warningred)
            val entersection: TextView =  findViewById<TextView>(R.id.entersection)
            entersection.setTextColor(warningred)
        }

    }
    override fun onBackPressed() {
        finish()
    }


}