package com.example.muviminder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.muviminder.R
import com.example.muviminder.UserSettings
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {



    lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAuthStateListener()
        setUserSettings()
    }

    private fun setUserSettings(){
        var kullanici = FirebaseAuth.getInstance().currentUser
        if (kullanici != null){


        }
    }

    private fun initAuthStateListener() {
        myAuthStateListener = object :FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {

                var kullanici = p0.currentUser
                if (kullanici != null){

                }else{
                    var intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){

            R.id.menuSignOut ->{
                signOut()
                return true
            }
            R.id.HesapAyarları ->{
                var intent = Intent(this, UserSettings::class.java)
                startActivity(intent)
                return true
            }
            R.id.profil ->{
                var intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun onResume() {
        super.onResume()
        kullaciyiKontrolEt()
        setUserSettings()
    }

    private fun kullaciyiKontrolEt() {
        var kullanici = FirebaseAuth.getInstance().currentUser
        if (kullanici == null){
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        if (myAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
        }

    }
}