package com.example.muviminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MailAgain : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var mContext:FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        emailEditText=view.findViewById(R.id.etDialogMail)
        passwordEditText=view.findViewById(R.id.etDialogPassword)
        mContext = requireActivity()

        var btnIptal = view.findViewById<Button>(R.id.btnDialogIptal)
        btnIptal.setOnClickListener{
            dialog?.dismiss()
        }

        var btnGonder = view.findViewById<Button>(R.id.btnDialogGonder)
        btnGonder.setOnClickListener {

            if(emailEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().isNotEmpty()){

                mailSendAgain(emailEditText.text.toString(),passwordEditText.text.toString())

            }else{
                Toast.makeText(mContext,"Boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    private fun mailSendAgain(email: String, password: String) {

        var credential = EmailAuthProvider.getCredential(email,password)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener{
                task->
                if (task.isSuccessful){
                    mailAgainSend()
                    dialog?.dismiss()
                }else{
                    Toast.makeText(mContext,"email veya şifre hatalı",Toast.LENGTH_SHORT).show()

                }

            }

    }

    private fun mailAgainSend() {
        var kullanıcı = FirebaseAuth.getInstance().currentUser
        if (kullanıcı != null){
            kullanıcı.sendEmailVerification()
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(p0: Task<Void>) {

                        if (p0.isSuccessful){
                            Toast.makeText(mContext,"mail atıldı",Toast.LENGTH_SHORT).show()

                        }else{

                            Toast.makeText(mContext,"mail gönderilirken sorun oluştu" + p0.exception?.message,Toast.LENGTH_SHORT).show()

                        }
                    }

                })
        }
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MailAgain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}