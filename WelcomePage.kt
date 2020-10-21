package com.ulkeler.titanium.kaanb.ulkeler

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.system.exitProcess

class WelcomePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hosgeldiniz)


        if(isOnline() == true)
        {

                Handler().postDelayed({
                    val intent = Intent(this@WelcomePage, AnaPanel::class.java)
                    startActivity(intent)
                }, 1000)


        }
        else
        {
            val dialog = Dialog(this@WelcomePage)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.timeisup)
            val textView19 = dialog.findViewById(R.id.textView19) as TextView

            val button4 = dialog.findViewById(R.id.button4) as Button
            val button = dialog.findViewById(R.id.button) as Button

            button4.setOnClickListener {
                moveTaskToBack(true)
                exitProcess(-1)

            }
            button.setOnClickListener {
                val intent = Intent(this@WelcomePage, WelcomePage::class.java)
                dialog.dismiss()
                startActivity(intent)


            }

            dialog.show()
            Toast.makeText(this@WelcomePage,"HATALI", Toast.LENGTH_LONG).show()

        }
    }



    fun isOnline(): Boolean? {
        try {
            val p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            //return returnVal == 0
            return true
        } catch (e: Exception) {

            e.printStackTrace()
        }

        return true
    }

}
