package com.example.muviminder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_first_screen.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initMyAuthStateListener()







        tvKayıtOlLogin.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvSendMail.setOnClickListener {
            var dialogGoster = MailAgain()
            dialogGoster.show(supportFragmentManager,"goster")
        }

        tvPasswordRegen.setOnClickListener {
            var dialogSifreyiTekrarGonder = PasswordRemember()
            dialogSifreyiTekrarGonder.show(supportFragmentManager,"passwordRemember")
        }

        btnLogin.setOnClickListener {

            if (etMailLogin.text.isNotEmpty() && etPasswordLogin.text.isNotEmpty()) {
                progressBarShow()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    etMailLogin.text.toString(),
                    etPasswordLogin.text.toString()
                )
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                        override fun onComplete(p0: Task<AuthResult>) {

                            if (p0.isSuccessful) {

                                progressBarHide()

                                if (!p0.result.user?.isEmailVerified!!){
                                    FirebaseAuth.getInstance().signOut()


                                }else{


                                }
                                     //Toast.makeText(
                                 //   this@LoginActivity,
                                   // "başarılı giriş",
                                   // Toast.LENGTH_SHORT
                                //).show()


                            } else {

                                progressBarHide()

                                Toast.makeText(
                                    this@LoginActivity,
                                    "hatalı giriş " + p0.exception,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    })

            } else {
                Toast.makeText(this@LoginActivity, "boş alanları doldurunuz", Toast.LENGTH_SHORT)
                    .show()
            }

        }


    }


    private fun progressBarShow() {
        progressBarLogin.visibility = View.VISIBLE
    }

    private fun progressBarHide() {
        progressBarLogin.visibility = View.INVISIBLE
    }

    private fun initMyAuthStateListener() {

        myAuthStateListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {

                var kullanici = p0.currentUser
                if (kullanici != null){
                    if (kullanici.isEmailVerified){
                        Toast.makeText(
                            this@LoginActivity,
                            "mail adresini onayı yapılmış giriş yapabilirisiniz ",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()



                    }else{

                        Toast.makeText(
                            this@LoginActivity,
                            "mail adresini onaylayın ",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }

            }

        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
    }
}