package com.ashwin.vote_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button:Button = findViewById(R.id.get_started_button)

        val text:TextView = findViewById(R.id.textView)
        button.setOnClickListener{
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }
    }
}