package com.dicoding.mygithub2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavAkun::class],
    version = 1,
    exportSchema = false
)

abstract class LocalDatabase : RoomDatabase() {
    companion object{
        var INSTANCE : LocalDatabase? = null

        fun getDatabase(context: Context) :LocalDatabase?{
            if (INSTANCE == null){
                synchronized(LocalDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, "user database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteDao(): FavAkunDao

}