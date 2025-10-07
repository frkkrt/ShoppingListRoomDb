package com.fkurt.shoppinglistcomposeroom

import androidx.room.*

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Products>  // <-- Buradaki isim önemli

    @Insert
    suspend fun insert(product: Products)

    @Query("DELETE FROM Products")
    suspend fun deleteAllProducts()


}