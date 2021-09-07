package com.ashwin.vote_app

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.service.autofill.Validators.not
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.w3c.dom.Text

class login_page : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isloggedin: Boolean = false

    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

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
                val text_view = findViewById<TextView>(R.id.textView2)
                text_view.visibility = View.GONE
            }

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
                    val text_view = findViewById<TextView>(R.id.textView2)
                    text_view.text = "Not: OK1"
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
                            text_view.text = "Your email is: \n" + user.email.toString() + "\nThis is not a iswkoman.com email\nClear app data and reopen the app "
                            user.delete()
                            mAuth.signOut()
                        } else{
                            Log.v("SignInActivity", "Successfully authorised")
                            val text_view = findViewById<TextView>(R.id.textView2)
                            text_view.text = "Your email is: \n" + user.email.toString() + "\nIf email is incorrect, clear data of the app and reopen the app"
                            val otaButton:Button = findViewById(R.id.otp_button)
                            otaButton.text = "Start"
                            isloggedin = true
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                    val text_view = findViewById<TextView>(R.id.textView2)
                    text_view.text = "Not: OK"
                }
            }
    }
}