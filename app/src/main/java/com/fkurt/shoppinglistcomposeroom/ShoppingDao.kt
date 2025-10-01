package com.fkurt.shoppinglistcomposeroom

import androidx.room.*

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Products>  // <-- Buradaki isim önemli

    @Insert
    suspend fun insert(product: Products)

    @Update
    suspend fun productUpdate(products: Products)

    @Delete
    suspend fun personDelete(products: Products)


}