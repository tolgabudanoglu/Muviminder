package com.example.muviminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_user_settings.*


class UserSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        var kullanici = FirebaseAuth.getInstance().currentUser!!

        etSettingsKullaniciAdi.setText(kullanici.displayName.toString())
        etSettingsMailReal.setText(kullanici.email.toString())

        btnSettingsReset.setOnClickListener {

            FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .addOnCompleteListener{
                        task->
                    if (task.isSuccessful){
                        Toast.makeText(this@UserSettings,"şifre sıfırlama maili gönderildi", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this@UserSettings,"Yanlış mail"+task.exception?.message, Toast.LENGTH_SHORT).show()

                    }

                }

        }

        btnSettingsSave.setOnClickListener {

            if (etSettingsKullaniciAdi.text.toString().isNotEmpty() && etSettingsMailReal.text.toString().isNotEmpty()){

                if (!etSettingsKullaniciAdi.text.toString().equals(kullanici.displayName.toString())){

                    var updateAccount = UserProfileChangeRequest.Builder()
                        .setDisplayName(etSettingsKullaniciAdi.text.toString())
                        .build()
                    kullanici.updateProfile(updateAccount)
                        .addOnCompleteListener{
                            task->
                            if (task.isSuccessful){
                                Toast.makeText(this@UserSettings,"Değişiklikler yapıldı",Toast.LENGTH_SHORT).show()
                            }
                        }

                }

            }else{

                Toast.makeText(this@UserSettings,"boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
            }

        }







    }
}