package com.ulkeler.titanium.kaanb.ulkeler


import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdaptor_future(val myCardList: ArrayList<CardView_future>) : RecyclerView.Adapter<CustomAdaptor_future.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.recycler_view_future,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return myCardList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val cardView: CardView_future = myCardList[p1]
        p0?.textView18.text = ""+cardView.sure
        p0?.textView20.text = ""+cardView.email
        p0?.textView21.text = ""+cardView.dogru
        p0?.textView22.text = ""+cardView.yanlis





    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textView18 = itemView.findViewById(R.id.textView18) as TextView
        val textView20 = itemView.findViewById(R.id.textView20) as TextView
        val textView21 = itemView.findViewById(R.id.textView21) as TextView
        val textView22 = itemView.findViewById(R.id.textView22) as TextView






    }
}