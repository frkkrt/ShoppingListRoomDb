package com.fkurt.shoppinglistcomposeroom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var vt: Database
    private lateinit var pdao: ShoppingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vt = Database.databaseAccess(this)!!
        pdao = vt.getShoppingListDao()

        setContent {
            ShoppingListApp(dao = pdao)
        }
    }
}