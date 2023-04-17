package com.example.muviminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreenActivity: AppCompatActivity() {


    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)


        btnGoogle.setOnClickListener{
            signInWithGoogle()
        }

        btnLoginPage.setOnClickListener{
            val intent : Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignPage.setOnClickListener{
            val intent : Intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun signInWithGoogle(){
        auth = FirebaseAuth.getInstance()
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        val signIntent = googleSignInClient.signInIntent
        launcher.launch(signIntent)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googlesigninclient = GoogleSignIn.getClient(this,gso)
        googlesigninclient.signOut()
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val  task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }


    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUı(account)
            }

        }else{
            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUı(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent : Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }





}