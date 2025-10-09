package com.fkurt.shoppinglistcomposeroom

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ShoppingListApp(dao: ShoppingDao) {
    val items = remember { mutableStateListOf<String>() }

    // DB'den yükle ve ilk açılışta boşsa default ürün ekle
    LaunchedEffect(Unit) {
        var productList = dao.getAllProducts()
        if (productList.isEmpty()) {
            // DB boşsa 1 adet default ürün ekle
            dao.insert(Products(name = "Ürün"))
            productList = dao.getAllProducts()
        }
        items.clear()
        items.addAll(productList.map {
            it.name })

    }

    ShoppingListScreen(
        items = items,
        onSaveClick = {
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteAllProducts()
                items.filter { it.isNotBlank() }.forEach { name ->
                    dao.insert(Products(name = name))
                }
            }
        }
    )
}