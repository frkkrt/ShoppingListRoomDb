package com.fkurt.shoppinglistcomposeroom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val products:MutableList<String>)
    :RecyclerView.Adapter<Adapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProduct: EditText = itemView.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.textViewProduct.setText(products[position])
        holder.textViewProduct.addTextChangedListener { text ->
            products[position] = text.toString()
        }
        holder.textViewProduct.setOnFocusChangeListener { _,hasFocus ->
            if(hasFocus){
                currentFocusedPosition = position
            }
        }
    }
    private var currentFocusedPosition: Int = -1

    // ✅ Activity’den çağırmak için fonksiyonlar
    fun addItemAtFocused() {
        val pos = if (currentFocusedPosition in 0 until products.size) {
            currentFocusedPosition
        } else {
            products.size - 1  // Focus yoksa son satırın altına ekle
        }
        // Burada products.size ile karşılaştır, out of bounds olmasın
        val insertPos = (pos + 1).coerceAtMost(products.size)
        products.add(insertPos, "")
        notifyItemInserted(insertPos)
    }

    fun removeItemAtFocused() {
        val pos = if (currentFocusedPosition in 0 until products.size) {
            currentFocusedPosition
        } else {
            products.size - 1
        }

        if (products.isNotEmpty() && pos in products.indices) {
            products.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }
    fun getProducts(): List<String> = products
}