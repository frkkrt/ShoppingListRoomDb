package com.fkurt.shoppinglistcomposeroom

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Room
import androidx.room.RoomDatabase
@androidx.room.Database(entities = [Products::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun getShoppingListDao():ShoppingDao

    //javadadaki static kodun karşılığı companion objecttir.
    companion object
    {
        var INSTANCE:Database?=null

        fun databaseAccess(context: Context) :Database?
        {
            if(INSTANCE==null)
            {
                synchronized(Database::class)
                {
                    INSTANCE= Room.databaseBuilder(context.applicationContext,
                        Database::class.java,
                        "shoppinglist.sqlite")
                       .build()
                }
            }
            return INSTANCE
        }
    }


}