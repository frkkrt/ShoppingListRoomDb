package com.fkurt.shoppinglistcomposeroom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private val itemList = mutableListOf<String>("Satır 1") // Başlangıç satırı

    private lateinit var vt:Database
    private lateinit var pdao:ShoppingDao

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        vt = Database.databaseAccess(this)!!
        pdao = vt.getShoppingListDao()

        // DB sıfırlama: tüm kayıtları sil
        lifecycleScope.launch {
            val allProducts = pdao.getAllProducts()
            for (p in allProducts) {
                pdao.personDelete(p)
            }
        }

        // Adapter’i tek satırla başlat
        adapter = Adapter(mutableListOf("Satır 1"))
        recyclerView.adapter = adapter

        // Butonlara tıklama işlemleri
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnRemove = findViewById<Button>(R.id.btnDelete)

        btnAdd.setOnClickListener {
            adapter.addItemAtFocused()
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        btnRemove.setOnClickListener {
            adapter.removeItemAtFocused()
        }

        productsAdd()
        loadProducts()
    }
    fun loadProducts() {
        lifecycleScope.launch {
            val dao = Database.databaseAccess(this@MainActivity)?.getShoppingListDao()
            if (dao != null) {
                val productList = dao.getAllProducts() // DAO'daki fonksiyonun adıyla çağır
                Log.d("DB_LOAD", "DB ürün sayısı: ${productList.size}")
                for (p in productList) {
                    Log.d("DB_LOAD", "Ürün: ${p.name}")
                }

                // RecyclerView'e yükle
                val adapterList = if (productList.isNotEmpty()) {
                    productList.map { it.name }.toMutableList()
                } else {
                    mutableListOf("")
                }

                adapter = Adapter(adapterList)
                recyclerView.adapter = adapter
            }
        }
    }
    fun productsAdd() {
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            val productList = adapter.getProducts()

            lifecycleScope.launch {
                val dao = Database.databaseAccess(this@MainActivity)?.getShoppingListDao()
                if (dao != null) {
                    for (name in productList) {
                        if (name.isNotBlank()) {
                            dao.insert(Products(name = name))
                        }
                    }
                    Log.d("DB_SAVE", "Veriler kaydedildi: $productList")
                }
            }
        }
    }

}