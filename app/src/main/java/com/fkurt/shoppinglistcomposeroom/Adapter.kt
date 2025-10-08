package com.fkurt.shoppinglistcomposeroom

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener/*
class Adapter(private val products:MutableList<String>)
    :RecyclerView.Adapter<Adapter.MyViewHolder>(){

    private var currentFocusedPosition: Int = -1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProduct: EditText = itemView.findViewById(R.id.itemTextView)
        var currentWatcher: TextWatcher? = null  // 🔹 Son eklenen watcher saklanacak
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 🔹 Önce önceki watcher'ı kaldır
        holder.currentWatcher?.let {
            holder.textViewProduct.removeTextChangedListener(it)
        }

        // 🔹 TextView'ı güncel veriyle doldur
        holder.textViewProduct.setText(products[position])

        // 🔹 Yeni watcher oluştur
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION && pos < products.size) {
                    products[pos] = s.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // 🔹 Yeni watcher’ı kaydet ve ekle
        holder.currentWatcher = watcher
        holder.textViewProduct.addTextChangedListener(watcher)

        holder.textViewProduct.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currentFocusedPosition = holder.adapterPosition
            }
        }
    }

    fun addItemAtFocused() {
        val pos = if (currentFocusedPosition in 0 until products.size) {
            currentFocusedPosition
        } else {
            products.size - 1
        }

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
 */
