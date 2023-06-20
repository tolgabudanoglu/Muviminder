package com.example.muviminder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.muviminder.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnSign.setOnClickListener{

            if (etMail.text.isNotEmpty()&& etPassword.text.isNotEmpty() && etPasswordAgain.text.isNotEmpty() ){

                if (etPassword.text.toString().equals(etPasswordAgain.text.toString())){

                    newAccount(etMail.text.toString(),etPassword.text.toString())
                }
                else{
                    Toast.makeText(this,"şifreler aynı değil",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this,"boş lalnları doldur",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun newAccount(mail: String, password: String) {

        progressBarShow()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0.isSuccessful){

                        Toast.makeText(this@RegisterActivity,"kayıt başarılı",Toast.LENGTH_SHORT).show()
                        mailSend()
                        FirebaseAuth.getInstance().signOut()
                    }
                    else{

                        Toast.makeText(this@RegisterActivity,"kayıt başarırısız" + p0.exception?.message,Toast.LENGTH_SHORT).show()
                    }


                }

            } )
        progressBarHide()



    }

    private fun mailSend() {
        var kullanıcı = FirebaseAuth.getInstance().currentUser
        if (kullanıcı != null){
            kullanıcı.sendEmailVerification()
                .addOnCompleteListener(object :OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {

                        if (p0.isSuccessful){
                            Toast.makeText(this@RegisterActivity,"mail atıldı",Toast.LENGTH_SHORT).show()

                        }else{

                            Toast.makeText(this@RegisterActivity,"mail gönderilirken sorun oluştu" + p0.exception?.message,Toast.LENGTH_SHORT).show()

                        }
                    }

                })
        }
    }

    private fun progressBarShow(){
        progressBar.visibility = View.VISIBLE
    }
    private fun progressBarHide(){
        progressBar.visibility = View.INVISIBLE
    }
}