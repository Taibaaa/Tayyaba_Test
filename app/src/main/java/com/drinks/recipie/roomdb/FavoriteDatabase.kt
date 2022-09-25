package com.drinks.recipie.roomdb

import android.content.Context
import androidx.room.*
import com.drinks.recipie.models.FavoriteModel
import com.drinks.recipie.util.ConverterClass


@Database(entities = arrayOf(FavoriteModel::class), version = 1, exportSchema = false)
@TypeConverters(ConverterClass::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun getFavoriteDao():FavoriteDao
    companion object{

        @Volatile
        private var INSTANCE1: FavoriteDatabase? = null

        fun getDatabase(context: Context):FavoriteDatabase{
            return INSTANCE1 ?: synchronized(this)
            {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_Database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE1= instance
                instance

            }
        }
    }
}