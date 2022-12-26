package com.example.muviminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_user_settings.*


class UserSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        var kullanici = FirebaseAuth.getInstance().currentUser!!

        etSettingsKullaniciAdi.setText(kullanici.displayName.toString())
        etSettingsPasswordReal.setText("**************")

        btnSettingsReset.setOnClickListener {

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@UserSettings,
                            "şifre sıfırlama maili gönderildi",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@UserSettings,
                            "Yanlış mail" + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

        }

        btnSettingsSave.setOnClickListener {

            if (etSettingsKullaniciAdi.text.toString()
                    .isNotEmpty() && etSettingsPasswordReal.text.toString().isNotEmpty()
            ) {

                if (!etSettingsKullaniciAdi.text.toString()
                        .equals(kullanici.displayName.toString())
                ) {

                    var updateAccount = UserProfileChangeRequest.Builder()
                        .setDisplayName(etSettingsKullaniciAdi.text.toString())
                        .build()
                    kullanici.updateProfile(updateAccount)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@UserSettings,
                                    "Değişiklikler yapıldı",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }

            } else {

                Toast.makeText(this@UserSettings, "boş alanları doldurunuz", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        btnSettingsPasswordAndMail.setOnClickListener {

            if (etSettingsPasswordReal.text.toString().isNotEmpty()) {

                var credential = EmailAuthProvider.getCredential(
                    kullanici.email.toString(),
                    etSettingsPasswordReal.text.toString()
                )
                kullanici.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            UserSettingLayout.visibility = View.VISIBLE
                            btnSettingsUpdateMail.setOnClickListener {
                                mailAdressUpdate()
                            }
                            btnSettingsUpdatePassword.setOnClickListener {
                                passwordUpdate()
                            }
                        } else {
                            Toast.makeText(
                                this@UserSettings,
                                "mevcut şifre yanlış",
                                Toast.LENGTH_SHORT
                            ).show()
                            UserSettingLayout.visibility = View.INVISIBLE
                        }

                    }

            } else {

                Toast.makeText(
                    this@UserSettings,
                    "güncellemeler için mevcut şifreyi yazınız",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }

    private fun passwordUpdate() {
        var kullanici = FirebaseAuth.getInstance().currentUser!!
        if (kullanici != null) {
            kullanici.updatePassword(etSettingsUpdatePassword.text.toString())
                .addOnCompleteListener { task ->
                    Toast.makeText(
                        this@UserSettings,
                        "şifreniz değiştirildi tekrar giriş yapınız",
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseAuth.getInstance().signOut()
                    toLoginPage()
                }
        }
    }

    private fun mailAdressUpdate() {
        var kullanici = FirebaseAuth.getInstance().currentUser!!
        if (kullanici != null) {

            FirebaseAuth.getInstance().fetchSignInMethodsForEmail(etSettingUpdateMail.text.toString())
                .addOnCompleteListener{
                    task->
                    if (task.isSuccessful){

                        if (task.getResult().signInMethods?.size ==1){
                            Toast.makeText(
                                this@UserSettings,
                                "mail kullınımda başka mail yazınız",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            kullanici.updateEmail(etSettingUpdateMail.text.toString())
                                .addOnCompleteListener { task ->
                                    Toast.makeText(
                                        this@UserSettings,
                                        "mailiniz değiştirildi tekrar giriş yapınız",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    mailSend()
                                    FirebaseAuth.getInstance().signOut()
                                    toLoginPage()

                                }
                        }
                    }else{

                        Toast.makeText(
                            this@UserSettings,
                            "mail güncellenemedi",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }


        }
    }
    fun toLoginPage(){
        var intent = Intent(this@UserSettings,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun mailSend() {
        var kullanıcı = FirebaseAuth.getInstance().currentUser
        if (kullanıcı != null){
            kullanıcı.sendEmailVerification()
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(p0: Task<Void>) {

                        if (p0.isSuccessful){
                            Toast.makeText(this@UserSettings,"mail atıldı",Toast.LENGTH_SHORT).show()

                        }else{

                            Toast.makeText(this@UserSettings,"mail gönderilirken sorun oluştu" + p0.exception?.message,Toast.LENGTH_SHORT).show()

                        }
                    }

                })
        }
    }
}