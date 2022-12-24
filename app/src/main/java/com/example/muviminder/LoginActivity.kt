package com.example.muviminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        tvKayıtOlLogin.setOnClickListener{
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{

            if (etMailLogin.text.isNotEmpty()&& etPasswordLogin.text.isNotEmpty()){
                progressBarShow()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etMailLogin.text.toString(),etPasswordLogin.text.toString())
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {

                            if (p0.isSuccessful){

                                progressBarHide()
                                Toast.makeText(this@LoginActivity,"başarılı giriş",Toast.LENGTH_SHORT).show()

                            }else{

                                progressBarHide()
                                Toast.makeText(this@LoginActivity,"hatalı giriş "+p0.exception,Toast.LENGTH_SHORT).show()
                            }
                        }

                    })

            }else{
                Toast.makeText(this@LoginActivity,"boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun progressBarShow(){
        progressBarLogin.visibility = View.VISIBLE
    }
    private fun progressBarHide(){
        progressBarLogin.visibility = View.INVISIBLE
    }
}