package com.ramazantiftik.koincrptocrazy.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramazantiftik.koincrptocrazy.databinding.RecyclerRowBinding
import com.ramazantiftik.koincrptocrazy.model.Crypto

class RecyclerAdapter(private val cryptoList: ArrayList<Crypto>, private val listener: Listener) : RecyclerView.Adapter<RecyclerAdapter.CryptoViewHolder>() {

    interface Listener{
        fun onItemClick(crypto: Crypto)
    }

    //recyclerRow background colors
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")


    class CryptoViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position%8]))
        holder.binding.cryptoNameText.setText(cryptoList.get(position).currency)
        holder.binding.cryptoPriceText.setText(cryptoList.get(position).price)
    }

}