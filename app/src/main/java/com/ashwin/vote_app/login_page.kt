package com.ashwin.vote_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.StringBuilder


class login_page : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isloggedin: Boolean = false
    private lateinit var mail_id : String

    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        Log.i(this.toString(),"LoginPage init")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        val otaButton:Button = findViewById(R.id.otp_button)
        otaButton.setOnClickListener{
            if (!isloggedin) {
                signIn()
                val text_view = findViewById<TextView>(R.id.textView2)
                text_view.text = "Working on it...."
            } else
            {
                val updootinfo = Intent(this, updateInfo::class.java)
                Toast.makeText(applicationContext,  "TESTMODE:EMAIL HIDDEN" , Toast.LENGTH_LONG).show()
                startActivity(updootinfo)
            }

        }

        val signoutbutton:Button = findViewById(R.id.signout_button)
        signoutbutton.setOnClickListener{
            signOut()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            }
            else{
                Log.w("SignInActivity",exception.toString())
                val text_view = findViewById<TextView>(R.id.textView2)
                text_view.text = exception.toString()
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    val text_view = findViewById<TextView>(R.id.textView2)
                    if (user != null) {
                        text_view.text = user.email.toString()
                        if (!user.email.toString().endsWith("@iswkoman.com")){
                            Log.w("SignInActivity", "signInWithCredential:not ISWK Org")
                            val text_view = findViewById<TextView>(R.id.textView2)
                            var displayedText  = StringBuilder()
                            displayedText.append(getString(R.string.Email_Preview)).append("\n").append("TESTMODE: EMAIL HIDDEN").append("\n").append(getString(R.string.notISWK)).append("\n").append(getString(R.string.Close_Reopen))
                            text_view.text = displayedText
                        } else{
                            Log.v("SignInActivity", "Successfully authorised")
                            val text_view = findViewById<TextView>(R.id.textView2)
                            var displayedText  = StringBuilder()
                            displayedText.append(getString(R.string.Email_Preview)).append("\n").append("TESTMODE: EMAIL HIDDEN").append("\n").append(getString(R.string.Close_Reopen))
                            text_view.text = displayedText
                            val otaButton:Button = findViewById(R.id.otp_button)
                            otaButton.text = getString(R.string.Start)
                            isloggedin = true
                            this.mail_id = user.email.toString()
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                    val text_view = findViewById<TextView>(R.id.textView2)
                    text_view.text = task.exception.toString()
                }
            }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {})
    }
}