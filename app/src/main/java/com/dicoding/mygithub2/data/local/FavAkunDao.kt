package com.dicoding.mygithub2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavAkunDao {

    @Insert
    fun addFavorite(favAkun: FavAkun)

    @Query("SELECT * FROM fav_user")
    fun getFavAkun(): LiveData<List<FavAkun>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    fun checkAkun(id: Long): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    fun removeFavorite(id: Long): Int
}