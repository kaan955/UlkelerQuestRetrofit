package com.ulkeler.titanium.kaanb.ulkeler

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import kotlinx.android.synthetic.main.recycler_view_future.*

class StatUser: AppCompatActivity()
{


    var counter: Long = 0
    var counterStrike= -1
    lateinit var map:Map<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statuser)

        val recyclerView_future = findViewById(R.id.recyclerView_future) as RecyclerView
        recyclerView_future.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val cardView_future = ArrayList<CardView_future>()


        while(counterStrike <= counter ) {
            counterStrike += 1
            FirebaseDatabase.getInstance().reference
                .child("stat")
                .child(counterStrike.toString())


                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        map = p0.value as Map<String, Any>
                        counter = p0.childrenCount






                        cardView_future.add(
                            CardView_future(1, 1, 1, "" + map["your_email"].toString())
                        )



                    }


                })


        }


        val adapter_future = CustomAdaptor_future(cardView_future)
        recyclerView_future.adapter = adapter_future


    }






}