package com.ashwin.vote_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

    }

    override fun onBackPressed() {
        finish()
    }
}