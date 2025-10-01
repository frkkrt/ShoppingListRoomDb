package com.fkurt.shoppinglistcomposeroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
@Entity(tableName = "products")
data class Products(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
