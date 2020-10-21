package com.ulkeler.titanium.kaanb.ulkeler

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.ana_panel.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ulkeler.titanium.kaanb.ulkeler.MainActivity.Companion.giris


class AnaPanel :AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ana_panel)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser

        textView9.setOnClickListener()
        {
            val intent = Intent(this@AnaPanel, StatUser::class.java)
            startActivity(intent)
        }

    

        imageView3.setOnClickListener()
        {
            if (user != null || user?.email.equals("null")) {
                val currentUser = auth.currentUser


                val dialog = Dialog(this@AnaPanel)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.userinformation)   //Dialog XML

                val button8 = dialog.findViewById(R.id.button8) as Button
                val button7 = dialog.findViewById(R.id.button7) as Button
                val textView15 = dialog.findViewById(R.id.textView15) as TextView

                textView15.text = "" + textView15.text.toString() +   currentUser?.email.toString()


                button8.setOnClickListener {


                    Firebase.auth.signOut()

                    val intent = Intent(this@AnaPanel, AnaPanel::class.java)
                    startActivity(intent)

                    dialog.dismiss()
                }



                dialog.show()

            } else {
                val dialog = Dialog(this@AnaPanel)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.userinformation)   //Dialog XML

                val button8 = dialog.findViewById(R.id.button8) as Button
                button8.text = "Ana Panel"

                val textView15 = dialog.findViewById(R.id.textView15) as TextView
                val textView13 = dialog.findViewById(R.id.textView13) as TextView
                val textView16 = dialog.findViewById(R.id.textView16) as TextView
                val editText6 = dialog.findViewById(R.id.editText6) as EditText
                val button7 = dialog.findViewById(R.id.button7) as Button



                textView15.text = "- Üye girişi yapmadınız.\n\n- Üye girişi yapmak için aşağıda ki butona tıklayınız."
                textView13.setVisibility(View.GONE)
                textView16.setVisibility(View.GONE)
                button7.setVisibility(View.GONE)
                editText6.setVisibility(View.GONE)



                button8.setOnClickListener {


                    val intent = Intent(this@AnaPanel, Register::class.java)
                    startActivity(intent)


                }

                dialog.show()
            }

    }







    }

    fun go(view: View?)
    {

        val textview8 = findViewById<TextView>(R.id.textView8)

        val intent = Intent(this@AnaPanel, MainActivity::class.java)
        MainActivity.currentSecond = 15
        MainActivity.toplam_currentSecond = 0
        MainActivity.toplam_dogru_sayisi = 0
        MainActivity.toplam_yanlis_sayisi = 0
        MainActivity.giris = 0
        startActivity(intent)

    }



}