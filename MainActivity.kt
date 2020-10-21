package com.ulkeler.titanium.kaanb.ulkeler

import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import androidx.core.content.ContextCompat
import com.androchef.happytimer.countdowntimer.CircularCountDownView
import com.androchef.happytimer.countdowntimer.HappyTimer
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.StrictMode
import android.widget.EditText
import android.widget.ImageView
import com.ahmadrosid.svgloader.SvgLoader
import com.google.android.gms.ads.*
import com.google.firebase.FirebaseApp

import com.google.cloud.translate.Translate
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import com.google.firebase.ktx.Firebase


import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd
    // private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    companion object {
        lateinit var cevaptasiyici: String
        lateinit var  countDownTimer: CountDownTimer
        var currentTime:Long =30000
        var currentSecond: Int = 15
        var toplam_currentSecond: Int = 0
        var toplam_dogru_sayisi: Int = 0
        var toplam_yanlis_sayisi: Int = 0

        var dogru_sayisi: Int = 0
        var yanlis_sayisi: Int = 0
        var giris: Int = 0
        var reklamizlendimi: Int = 0
        lateinit var hasLowercase:Pattern
        lateinit var hasSpecial:Pattern

        lateinit var matcher: Matcher
        lateinit var matcher2: Matcher

    }

    private var translate: Translate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getTranslateService()


        // MobileAds.initialize(this@MainActivity)
        val adView = AdView(this@MainActivity)
        // myAdView = findViewById<View>(R.id.myAdView) as AdView
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        MobileAds.initialize(this@MainActivity) {
            val adRequest = AdRequest.Builder().build()
            myAdView.loadAd(adRequest)


        }

        MobileAds.initialize(this,
            "ca-app-pub-3940256099942544~3347511713")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())





        val extras = intent.extras
        if (extras != null) {
            toplam_currentSecond += extras.getInt("key")
            giris = extras.getInt("key1")
            toplam_dogru_sayisi += extras.getInt("key2")
            toplam_yanlis_sayisi += extras.getInt("key3")
            reklamizlendimi = extras.getInt("key6")

            //The keyhere must match that used in the other activity

            auth = Firebase.auth
            val user = Firebase.auth.currentUser


            if (user != null ||  !user?.email.equals(null)) {
                val currentUser = auth.currentUser

                var map = mutableMapOf<String, Any>()


                val strs = user?.email.toString().split("@", ".").toTypedArray()
                var size_strs = strs.size
                var my_strs = ""
                var x = 0
                while (x < size_strs) {
                    my_strs += strs[x]


                    x++
                }



                map["your_sure"] = toplam_currentSecond
                map["your_dogru"] = dogru_sayisi
                map["your_yanlis"] = yanlis_sayisi


/*
                FirebaseDatabase.getInstance().reference
                    .child("stat")
                    .child(my_strs)
                    .updateChildren(map)
                    */
            }

        }



        if(giris == 0)
        {


            val dialog = Dialog(this@MainActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.timeisup)   //Dialog XML
            val textView19 = dialog.findViewById(R.id.textView19) as TextView
            val button4 = dialog.findViewById(R.id.button4) as Button
            val button = dialog.findViewById(R.id.button) as Button
            val textView5 = dialog.findViewById(R.id.textView5) as TextView
            button4.setVisibility(View.INVISIBLE)


            textView19.text = "45 Saniye Boyunca Soruları yanıtlayacaksınız. \n\nSüre bitiminde Verdiğiniz Cevaplar Size Ekstra Süre kazandıracak!"




            button.text= "Hazırım"



            button.setOnClickListener {



                dialog.dismiss()
                happy_time()



            }
            dialog.show()


        }else
        {


            val dialog = Dialog(this@MainActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.timeisup)   //Dialog XML
            val textView19 = dialog.findViewById(R.id.textView19) as TextView
            val button4 = dialog.findViewById(R.id.button4) as Button
            val button = dialog.findViewById(R.id.button) as Button

            val textView5 = dialog.findViewById(R.id.textView5) as TextView


            // myCircularCountDownView.getTotalSeconds()

            textView19.text = "Şimdiye kadar " + (toplam_currentSecond) +"sn dayandınız."

            textView5.text = "Doğru sayısınız: " + dogru_sayisi + " x15= " +dogru_sayisi*15 + "sn kazandırdı. \nYanlış sayınız: " + yanlis_sayisi + " x3= "+yanlis_sayisi*3 +"sn kaybettirdi."
            textView5.setVisibility(View.VISIBLE)
            currentSecond = (dogru_sayisi * 15) - (yanlis_sayisi * 3)

            if(currentSecond > 0 )
            {
                button.text = "Devam("+currentSecond+"sn)"
                button4.text = "Ana Menu"
                button4.setOnClickListener {


                    val intent = Intent(this@MainActivity, AnaPanel::class.java)
                    intent.putExtra("key", 0)
                    intent.putExtra("key1", 0)
                    intent.putExtra("key2", 0)
                    intent.putExtra("key3", 0)

                    intent.putExtra("key6", 1)

                    startActivity(intent)



                }

                button.setOnClickListener {
                    myCircularCountDownView.setVisibility(View.GONE)
                    myCircularCountDownView.stopTimer()

                    var Deneme: Boolean = soru_secici()

                    happy_time()

                    dialog.dismiss()
                }}

            else if(currentSecond <= 0 && reklamizlendimi == 0)
            {
                textView19.text = "Şimdiye kadar " + (toplam_currentSecond) +"sn dayandınız."
                textView5.text = "Doğru sayısınız: " + toplam_dogru_sayisi + " x15= " +toplam_dogru_sayisi*15 + "sn kazandırdı. \nYanlış sayınız: " + toplam_yanlis_sayisi + " x3= "+toplam_yanlis_sayisi*3 +"sn kaybettirdi. \nSüreniz Bitti."
                textView5.setVisibility(View.VISIBLE)
                button.text = "Reklam Izle (+30SN)"
                button4.text = "Ana Menu"
                button.setOnClickListener {

                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()

                        reklamizlendimi = 1
                        currentSecond = 30
                        happy_time()
                        dialog.dismiss()

                    } else {
                        currentSecond = 30
                        var Deneme: Boolean = soru_secici()
                        reklamizlendimi = 1
                        happy_time()
                        dialog.dismiss()
                    }
                }

                button4.setOnClickListener {
                    val intent = Intent(this@MainActivity, AnaPanel::class.java)
                    startActivity(intent)

                }

            }else
            {
                textView19.text = "Şimdiye kadar " + (toplam_currentSecond) +"sn dayandınız."
                textView5.text = "Doğru sayısınız: " + toplam_dogru_sayisi + " x15= " +toplam_dogru_sayisi*15 + "sn kazandırdı. \nYanlış sayınız: " + toplam_yanlis_sayisi + " x3= "+toplam_yanlis_sayisi*3 +"sn kaybettirdi.\nHiç Süreniz Kalmadı."
                textView5.setVisibility(View.VISIBLE)

                button.text = "Yeni Oyun"
                button4.text = "Ana Menu"



                button.setOnClickListener {

                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.putExtra("key6", 0)
                    intent.putExtra("key", 0)
                    intent.putExtra("key1", 0)
                    intent.putExtra("key2", 0)
                    intent.putExtra("key3", 0)

                     currentSecond = 15
                     toplam_currentSecond = 0
                     toplam_dogru_sayisi = 0
                     toplam_yanlis_sayisi = 0
                    startActivity(intent)

                }
                button4.setOnClickListener {
                    val intent = Intent(this@MainActivity, AnaPanel::class.java)



                    startActivity(intent)

                }




            }





            dialog.show()


        }





        dogru_sayisi = 0
        yanlis_sayisi = 0


        var Deneme:Boolean = soru_secici()


    }




    fun soru_secici():Boolean
    {
        hasLowercase = Pattern.compile(".*[a-z].*")
        hasSpecial = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*")
        //matcher = hasLowercase.matcher()

        myCircularCountDownView.setVisibility(View.VISIBLE)
        var apiInterface = ApiClient.client?.create(ApiInterface::class.java)
        var apiCall = apiInterface?.tumVerileriGetir()


        apiCall?.enqueue(object : Callback<List<UlkeBilgileri>>{
            override fun onFailure(call: Call<List<UlkeBilgileri>>, t: Throwable) {
                Log.e("Hata",""+ t?.printStackTrace())

            }




            override fun onResponse(call: Call<List<UlkeBilgileri>>, response: Response<List<UlkeBilgileri>>) {

                var ulkeyiBelirle: Int = Random().nextInt(249)
                var mySoruKategori = arrayOf<String>("name","capital","flag")


                var myCevapKategori = arrayOf<String>("name","capital")
                var soruyuBelirle: Int = Random().nextInt(3)
                var cevapKonusunuBelirle = 0
                if(soruyuBelirle == 0)
                {
                    cevapKonusunuBelirle = 1
                }else if(soruyuBelirle == 1)
                {
                    cevapKonusunuBelirle = 0

                }else if(soruyuBelirle == 2)
                {
                    cevapKonusunuBelirle = 0
                }


                var sorumuz:String = mySoruKategori[soruyuBelirle]
                var sorunun_konusu:String = myCevapKategori[cevapKonusunuBelirle]

                if(sorumuz.equals("name") && sorunun_konusu.equals("capital") )
                {
                    question.setVisibility(View.VISIBLE)

                    imageView.setVisibility(View.GONE)
                    var current_soru =  "" + response?.body()?.get(ulkeyiBelirle)?.name.toString()
                    var current_cevap =  "" +  response?.body()?.get(ulkeyiBelirle)?.capital.toString()

                    current_cevap = translate(current_cevap)


                    cevaptasiyici = current_cevap
                    var ulkeyiBelirle2: Int = Random().nextInt(249)
                    var ulkeyiBelirle3: Int = Random().nextInt(249)
                    var ulkeyiBelirle4: Int = Random().nextInt(249)

                    var yanlis1 =  "" +  response?.body()?.get(ulkeyiBelirle2)?.capital.toString()
                    var yanlis2 =  "" +  response?.body()?.get(ulkeyiBelirle3)?.capital.toString()
                    var yanlis3 =  "" +  response?.body()?.get(ulkeyiBelirle4)?.capital.toString()

                    var myChooser =  arrayOf<String>(current_cevap,yanlis1,yanlis2,yanlis3)

                    for (i in myChooser.size - 1 downTo 0) {
                        val j = Math.floor(Math.random() * (i + 1)).toInt()
                        val temp = myChooser[i]
                        myChooser[i] = myChooser[j]
                        myChooser[j] = temp
                    }



                    var cevirilmissoru = translate(current_soru)
                    var  secilmis1 = translate(myChooser[0])
                    var  secilmis2 = translate(myChooser[1])
                    var  secilmis3 = translate(myChooser[2])
                    var  secilmis4 = translate(myChooser[3])


                    question.text = "'" + cevirilmissoru+"'" + " ülkesinin başkenti neresidir?"

                    textView.text =  "" + secilmis1
                    textView2.text = ""+ secilmis2
                    textView3.text = ""+ secilmis3
                    textView4.text = ""+ secilmis4






                    textView6.text = ""+ current_cevap

                }else if(sorumuz.equals("flag") && sorunun_konusu.equals("name"))
                {
                    question.setVisibility(View.GONE)

                    imageView.setVisibility(View.VISIBLE)
                    SvgLoader.pluck()
                        .with(this@MainActivity)
                        .load("https://restcountries.eu/data/afg.svg", imageView)

                    var current_soru =  "" + response?.body()?.get(ulkeyiBelirle)?.flag.toString()
                    var current_cevap =  "" +  response?.body()?.get(ulkeyiBelirle)?.name.toString()

                    SvgLoader.pluck()
                        .with(this@MainActivity)

                        .load(current_soru, imageView)

                    current_cevap = translate(current_cevap)


                    cevaptasiyici = current_cevap
                    var ulkeyiBelirle2: Int = Random().nextInt(249)
                    var ulkeyiBelirle3: Int = Random().nextInt(249)
                    var ulkeyiBelirle4: Int = Random().nextInt(249)

                    var yanlis1 =  "" +  response?.body()?.get(ulkeyiBelirle2)?.capital.toString()
                    var yanlis2 =  "" +  response?.body()?.get(ulkeyiBelirle3)?.capital.toString()
                    var yanlis3 =  "" +  response?.body()?.get(ulkeyiBelirle4)?.capital.toString()

                    var myChooser =  arrayOf<String>(current_cevap,yanlis1,yanlis2,yanlis3)

                    for (i in myChooser.size - 1 downTo 0) {
                        val j = Math.floor(Math.random() * (i + 1)).toInt()
                        val temp = myChooser[i]
                        myChooser[i] = myChooser[j]
                        myChooser[j] = temp
                    }

                    var cevirilmissoru = translate(current_soru)
                    var  secilmis1 = translate(myChooser[0])
                    var  secilmis2 = translate(myChooser[1])
                    var  secilmis3 = translate(myChooser[2])
                    var  secilmis4 = translate(myChooser[3])


                    question.text = "'" + cevirilmissoru+"'" + " ülkesinin başkenti neresidir?"

                    textView.text =  "" + secilmis1
                    textView2.text = ""+ secilmis2
                    textView3.text = ""+ secilmis3
                    textView4.text = ""+ secilmis4



                    textView6.text = ""+ current_cevap




                }else if(sorumuz.equals("capital") && sorunun_konusu.equals("name"))
                {
                    question.setVisibility(View.VISIBLE)

                    imageView.setVisibility(View.GONE)
                    var current_soru =  "" + response?.body()?.get(ulkeyiBelirle)?.capital.toString()
                    var current_cevap =  "" +  response?.body()?.get(ulkeyiBelirle)?.name.toString()

                    current_cevap = translate(current_cevap)


                    cevaptasiyici = current_cevap
                    var ulkeyiBelirle2: Int = Random().nextInt(249)
                    var ulkeyiBelirle3: Int = Random().nextInt(249)
                    var ulkeyiBelirle4: Int = Random().nextInt(249)

                    var yanlis1 =  "" +  response?.body()?.get(ulkeyiBelirle2)?.capital.toString()
                    var yanlis2 =  "" +  response?.body()?.get(ulkeyiBelirle3)?.capital.toString()
                    var yanlis3 =  "" +  response?.body()?.get(ulkeyiBelirle4)?.capital.toString()

                    var myChooser =  arrayOf<String>(current_cevap,yanlis1,yanlis2,yanlis3)

                    for (i in myChooser.size - 1 downTo 0) {
                        val j = Math.floor(Math.random() * (i + 1)).toInt()
                        val temp = myChooser[i]
                        myChooser[i] = myChooser[j]
                        myChooser[j] = temp
                    }



                    var cevirilmissoru = translate(current_soru)
                    var  secilmis1 = translate(myChooser[0])
                    var  secilmis2 = translate(myChooser[1])
                    var  secilmis3 = translate(myChooser[2])
                    var  secilmis4 = translate(myChooser[3])


                    question.text = "'" + cevirilmissoru+"'" + " hangi ülkenin başkentidir?"

                    textView.text =  "" + secilmis1
                    textView2.text = ""+ secilmis2
                    textView3.text = ""+ secilmis3
                    textView4.text = ""+ secilmis4



                    //question.text = "" + current_soru + "'in başkenti neresidir?"
                    // textView.text =  "" + myChooser[0]
                    //textView2.text = ""+ myChooser[1]
                    //textView3.text = ""+ myChooser[2]
                    //textView4.text = ""+ myChooser[3]






                    textView6.text = ""+ current_cevap



                }
            }

        })
        textView.isClickable = true
        textView2.isClickable = true
        textView3.isClickable = true
        textView4.isClickable = true

        return true

    }

    fun ClickTextView(view: View?) {

        val textview2 = findViewById<TextView>(R.id.textView2)
        val textview3 = findViewById<TextView>(R.id.textView3)
        val textview4 = findViewById<TextView>(R.id.textView4)

        (view as TextView).text.toString()
        textView.isClickable = false
        textView2.isClickable = false
        textView3.isClickable = false
        textView4.isClickable = false
        //  val countTime = timecount.text as String

        view.setBackgroundResource(R.color.waitingcolor)

        if ((view as TextView).text.toString().equals(""+cevaptasiyici)) {

            Handler().postDelayed({
                view.setBackgroundResource(R.color.true_)
            }, 200)
            dogru_sayisi += 1


            textView6.text = "Doğru cevap"
        } else if (textView.text.toString() == cevaptasiyici) {

            view.setBackgroundResource(R.color.false_)
            yanlis_sayisi += 1

            Handler().postDelayed({
                textView.setBackgroundResource(R.color.true_)
                val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink)
                textView.startAnimation(animation)
            }, 200)



        } else if (textview2.text.toString() == cevaptasiyici) {
            view.setBackgroundResource(R.color.false_)

            yanlis_sayisi += 1
            Handler().postDelayed({
                textview2.setBackgroundResource(R.color.true_)
                val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink)
                textview2.startAnimation(animation)
            }, 200)

        } else if (textview3.text.toString() == cevaptasiyici) {
            view.setBackgroundResource(R.color.false_)
            yanlis_sayisi += 1


            Handler().postDelayed({

                textview3.setBackgroundResource(R.color.true_)
                val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink)
                textview3.startAnimation(animation)
            }, 200)

        }else if (textView4.text.toString() == cevaptasiyici) {
            view.setBackgroundResource(R.color.false_)
            yanlis_sayisi += 1



            Handler().postDelayed({

                textview4.setBackgroundResource(R.color.true_)
                val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink)
                textview4.startAnimation(animation)
            }, 200)
        }
        else
            textView6.text = cevaptasiyici + "olmadı else içindeyiz " + (view as TextView).text.toString()

        Handler().postDelayed({
            textView.setBackgroundResource(R.drawable.my_border)
            textView2.setBackgroundResource(R.drawable.my_border)
            textView3.setBackgroundResource(R.drawable.my_border)
            textView4.setBackgroundResource(R.drawable.my_border)

            textView.isClickable = true
            textView2.isClickable = true
            textView3.isClickable = true
            textView4.isClickable = true

            var Deneme:Boolean = soru_secici()
        }, 1000)
    }


    fun happy_time()
    {

        myCircularCountDownView.isTimerTextShown = true
        myCircularCountDownView.timerType = HappyTimer.Type.COUNT_DOWN
        myCircularCountDownView.timerTextFormat = CircularCountDownView.TextFormat.MINUTE_SECOND
        myCircularCountDownView.strokeThicknessForeground = 10f
        myCircularCountDownView.strokeThicknessBackground = 10f
        myCircularCountDownView.strokeColorBackground = ContextCompat.getColor(this, R.color.colorGrey)
        myCircularCountDownView.strokeColorForeground = ContextCompat.getColor(this, R.color.colorLightBlue)
        myCircularCountDownView.timerTextColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        myCircularCountDownView.timerTextIsBold = true
        myCircularCountDownView.timerTextSize = 13f //this will automatically converted to sp value.

        //Initialize Your Timer with seconds
        myCircularCountDownView.initTimer(currentSecond)

        //set OnTickListener for getting updates on time. [Optional]
        myCircularCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {
                val intent = Intent(this@MainActivity, this@MainActivity::class.java)
                giris += 1
                intent.putExtra("key", currentSecond)
                intent.putExtra("key1", giris)
                intent.putExtra("key2", dogru_sayisi)
                intent.putExtra("key3", yanlis_sayisi)
                intent.putExtra("key6", reklamizlendimi )


                startActivity(intent)

                auth = Firebase.auth
                val user = Firebase.auth.currentUser


                if (user != null || !user?.email.equals(null)) {
                    val currentUser = auth.currentUser

                    var map5 = mutableMapOf<String, Any>()


                    val strs = user?.email.toString().split("@", ".").toTypedArray()
                    var size_strs = strs.size
                    var my_strs = ""
                    var x = 0
                    while (x < size_strs) {
                        my_strs += strs[x]


                        x++


                    }
                    FirebaseDatabase.getInstance().reference
                        .child("stat")
                        .child(my_strs)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                var map = p0?.value as Map<String,Any>
                                var map5 = mutableMapOf<String, Any>()
                                map["your_sure"].toString().toInt()


                                if(map["your_sure"].toString().toInt() < toplam_currentSecond) {

                                    map5["your_sure"] = toplam_currentSecond
                                    map5["your_dogru"] = toplam_dogru_sayisi
                                    map5["your_yanlis"] = toplam_yanlis_sayisi


                                FirebaseDatabase.getInstance().reference
                                    .child("stat")
                                    .child(my_strs)
                                    .updateChildren(map5)
                            }


                        }

                        })


                }



                textView.isClickable = false
                textView2.isClickable = false
                textView3.isClickable = false
                textView4.isClickable = false




            }
        })



        //set OnStateChangeListener [RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED] [Optional]
        myCircularCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                // write your code here for State Changes


            }
        })

        //Call these functions to perform actions
        //Start Timer
        myCircularCountDownView.startTimer()


        //Pause Timer
        // circularCountDownView.pauseTimer()

        //Resume Timer
        // circularCountDownView.resumeTimer()

        //Stop Timer
        //circularCountDownView.stopTimer()

        //Reset Timer
        //circularCountDownView.resetTimer()

        //get Total Seconds
        val totalSeconds = myCircularCountDownView.getTotalSeconds()



    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //This is used to hide/show 'Status Bar' & 'System Bar'. Swip bar to get it as visible.
        val decorView = window.decorView
        if (hasFocus) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun getTranslateService() {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            resources.openRawResource(R.raw.credentials).use { `is` ->
                val myCredentials = GoogleCredentials.fromStream(`is`)
                val translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                translate = translateOptions.service
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()

        }

    }

    private fun translate(cevirilecek:String):String {

        //Get input text to be translated:
        val originalText = cevirilecek
        val translation = translate!!.translate(originalText, Translate.TranslateOption.targetLanguage("tr"), Translate.TranslateOption.model("base"))


        textView7!!.text = translation.translatedText

        return translation.translatedText

    }




}
