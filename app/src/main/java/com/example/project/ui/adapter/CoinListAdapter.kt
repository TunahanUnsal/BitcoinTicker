package com.example.project.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.repository.coinService.reqres.Coin


class CoinListAdapter(private val coinList: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_coin, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coinList[position])
        holder.itemView.findViewById<ConstraintLayout>(R.id.view_general).startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.item_animation_fall_down))
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(coin: Coin) {

            Log.i("TAG", "bindItems: $coin")

            val rankTextView = itemView.findViewById<TextView>(R.id.rank)
            val symbolTextView = itemView.findViewById<TextView>(R.id.symbol)
            val nameTextView = itemView.findViewById<TextView>(R.id.name)
            val typeTextView = itemView.findViewById<TextView>(R.id.type)

            rankTextView.text = "#" + coin.rank.toString()
            symbolTextView.text = coin.symbol.toString()
            nameTextView.text = coin.name.toString()
            typeTextView.text = coin.type.toString()


        }
    }

}