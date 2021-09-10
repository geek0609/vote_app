package com.ashwin.vote_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button:Button = findViewById(R.id.get_started_button)

        mAuth = FirebaseAuth.getInstance()

        val text:TextView = findViewById(R.id.textView)

        button.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }
    }
}