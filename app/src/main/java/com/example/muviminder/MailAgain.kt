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


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MailAgain : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

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

        var btnIptal = view.findViewById<Button>(R.id.btnDialogIptal)
        btnIptal.setOnClickListener{
            dialog?.dismiss()
        }

        var btnGonder = view.findViewById<Button>(R.id.btnDialogGonder)
        btnGonder.setOnClickListener {
            Toast.makeText(activity,"Gönder tıklandı",Toast.LENGTH_SHORT).show()
        }


        return view
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