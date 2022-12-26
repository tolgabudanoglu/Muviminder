package com.example.muviminder

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth


class PasswordRemember : DialogFragment() {


    lateinit var emailEditText: EditText

    lateinit var mContext: FragmentActivity



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_password_remember, container, false)

        emailEditText = view.findViewById(R.id.etMailPasswordFragment)
        mContext = requireActivity()

        var btnIptal = view.findViewById<Button>(R.id.btnPaswordIptalFragment)
        btnIptal.setOnClickListener {
            dialog?.dismiss()
        }

        var btnGonder = view.findViewById<Button>(R.id.btnPasswordGonderFragment)
        btnGonder.setOnClickListener {

            FirebaseAuth.getInstance().sendPasswordResetEmail(emailEditText.text.toString())
                .addOnCompleteListener{
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(mContext,"şifre sıfırlama maili gönderildi",Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                    }else{
                        Toast.makeText(mContext,"Yanlış mail"+task.exception?.message,Toast.LENGTH_SHORT).show()

                    }

                }


        }

        return view
    }


}