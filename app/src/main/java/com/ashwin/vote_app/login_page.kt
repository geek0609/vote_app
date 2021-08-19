package com.ashwin.vote_app

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        val otaButton:Button = findViewById(R.id.otp_button)
        val otpCheck:Button = findViewById(R.id.otp_check_button)
        val otp:EditText = findViewById(R.id.enter_otp)
        otaButton.setOnClickListener{
            val emailEntered = findViewById<EditText>(R.id.editText).text
            var EmailFormatOK: Boolean = false
            val warning = findViewById<TextView>(R.id.incorrectPassword)
            if (emailEntered.endsWith("@iswkoman.com")){
                EmailFormatOK = true
                otp.visibility = View.VISIBLE
                otpCheck.visibility = View.VISIBLE
                warning.visibility = View.INVISIBLE
                otaButton.isEnabled = false
                val otp_toast = Toast.makeText(applicationContext, "OTP Sent\nCheck your email" , Toast.LENGTH_SHORT)
                otp_toast.setGravity(Gravity.CENTER, 0, 0)
                otp_toast.show()
            } else {
                EmailFormatOK =  false
                warning.visibility = View.VISIBLE
            }
            otpCheck.setOnClickListener{
                val otp_toast = Toast.makeText(applicationContext, "OTP Verified" , Toast.LENGTH_SHORT)
                otp_toast.setGravity(Gravity.CENTER, 0, 0)
                otp_toast.show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}