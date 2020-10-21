package com.ulkeler.titanium.kaanb.ulkeler

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register_kullanici.*

class Register : AppCompatActivity() {



    private lateinit var auth: FirebaseAuth
    companion object {

        var authHatasi:String = ""
        var  dataCounter: Long = 0


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth= Firebase.auth

        val user = Firebase.auth.currentUser

        //updateUI(currentUser)
        button5.setOnClickListener {
            val intent = Intent(this@Register, AnaPanel::class.java)

            startActivity(intent)


        }
        button3.setOnClickListener()
        {

            register()



        }


        button2.setOnClickListener()
        {

            if(editText.text.isNotEmpty() && editText2.text.isNotEmpty()) {
                auth.signInWithEmailAndPassword(editText.text.toString(), editText2.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("haya", "signInWithEmail:success")
                            Toast.makeText(
                                this@Register, "Başarılı.",
                                Toast.LENGTH_LONG
                            ).show()
                            val user = auth.currentUser
                            // updateUI(user)

                            val intent = Intent(this@Register, AnaPanel::class.java)

                            startActivity(intent)


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("haya", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // updateUI(null)


                                editText2.setText("")
                                Toast.makeText(this@Register, "Yanlış Email veya Şifre.", Toast.LENGTH_LONG).show()



                        }
                    }



            }else
            {

                Toast.makeText(this@Register, "Boş alanları doldurunuz..", Toast.LENGTH_LONG).show()


            }


        }







    }




  fun register()
  {
      val dialog = Dialog(this@Register)
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog.setCancelable(true)
      dialog.setContentView(R.layout.register_kullanici)   //Dialog XML
      val editText3 = dialog.findViewById(R.id.editText3) as EditText
      val editText4 = dialog.findViewById(R.id.editText4) as EditText
      val editText5 = dialog.findViewById(R.id.editText5) as EditText
      val textView12 = dialog.findViewById(R.id.textView12) as TextView
      val button6 = dialog.findViewById(R.id.button6) as Button


      FirebaseDatabase.getInstance().reference
          .child("stat")


          .addListenerForSingleValueEvent(object : ValueEventListener {
              override fun onCancelled(p0: DatabaseError) {

              }

              override fun onDataChange(p0: DataSnapshot) {

                  if(p0.childrenCount > 0) {
                  var map = p0.value as Map<String, Any>

                        dataCounter = p0.childrenCount
                  }

              }
          })







      button6.setOnClickListener {

          if(editText3.text.isNotEmpty() && editText4.text.isNotEmpty() && editText5.text.isNotEmpty())
          {
              if(android.util.Patterns.EMAIL_ADDRESS.matcher(editText3.text.toString()).matches())
              {

                  if(editText4.text.toString().equals(editText5.text.toString()))
                  {

                      auth.createUserWithEmailAndPassword(editText3.text.toString(),editText4.text.toString())
                          .addOnCompleteListener(this) { task ->
                              if (task.isSuccessful) {
                                  Toast.makeText(this@Register,"Başarılı",Toast.LENGTH_LONG).show()


                                var size = dataCounter

                                  val strs = editText3.text.toString().split("@",".").toTypedArray()
                                  var size_strs = strs.size
                                  var my_strs = ""
                                  var x = 0
                                  while (x < size_strs) {
                                      my_strs += strs[x]


                                      x++
                                  }

                                  var map = mutableMapOf<String,Any>()
                                  map["your_sure"] = 0
                                  map["your_dogru"] = 0
                                  map["your_yanlis"] = 0
                                  map["your_email"] =editText3.text.toString()
                                  map["your_kullaniciadi"] =""

                                  FirebaseDatabase.getInstance().reference
                                      .child("stat")
                                      .child((dataCounter+1).toString())
                                      .setValue(map)




                                  Handler().postDelayed({
                                      dialog.dismiss()
                                  }, 750)





                              } else {

                                  if(task.exception?.message.equals("The email address is already in use by another account."))
                                  {
                                      editText3.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.false_)))
                                      editText5.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.colorGrey)))
                                      editText4.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.colorGrey)))

                                      textView12.text  = "Bu e-mail zaten sistemde kayıtlı"

                                  }else if(task.exception?.message.equals("The given password is invalid. [ Password should be at least 6 characters ]"))
                                  {
                                      editText5.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.false_)))
                                      editText4.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.false_)))

                                      textView12.text  = "Şifre en az 6 karakterli olmalı"
                                  }else
                                      textView12.text  = "Hata, bilgilerinizi kontrol ediniz."
                              }

                          }


                  }else
                  {


                       editText5.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.false_)))
                       Toast.makeText(this@Register,"Şifreler aynı değil..",Toast.LENGTH_LONG).show()


                  }




              }else
              {

                  editText3.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.false_)))
                  Toast.makeText(this@Register,"Email Hatalı..",Toast.LENGTH_LONG).show()

              }

      }else{
              Toast.makeText(this@Register,"Boş alanları doldurunuz..",Toast.LENGTH_LONG).show()
          }





      }
      dialog.show()

  }


    fun yeniKayıt(email:String,password:String)
    {
        progressBarShow()


        var s:String = ""
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this@Register,"Başarılı",Toast.LENGTH_LONG).show()
                    Log.d("hata", "createUserWithEmail:success")


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("hata", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception?.message.toString()
                        ,
                        Toast.LENGTH_LONG).show()

                    authHatasi  =  task.exception?.message.toString()

                    //task.exception.
                    //updateUI(null)
                }


            }

    }

    fun progressBarShow()
    {
      //  progressBar.setVisibility(View.VISIBLE)
    }
    fun progressBarGizle()
    {
        progressBar.visibility = View.INVISIBLE
    }

}