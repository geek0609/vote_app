package com.ashwin.vote_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        val otaButton:Button = findViewById(R.id.otp_button)
        val otpCheck:Button = findViewById(R.id.otp_check_button)
        val otp:EditText = findViewById(R.id.enter_otp)
        otaButton.setOnClickListener{
            val intent:Intent = Intent(this, login_page::class.java)
            Toast.makeText(applicationContext, getString(R.string.otp_success), Toast.LENGTH_SHORT).show()
            otp.visibility = View.VISIBLE
            otpCheck.visibility = View.VISIBLE

            otpCheck.setOnClickListener{
                Toast.makeText(applicationContext, "OTP Verified" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}