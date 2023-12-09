package com.dicoding.mygithub2.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "fav_user")
data class FavAkun (

    @ColumnInfo(name = "username")
    val login: String,

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "image_url")
    val avatarUrl: String
): Serializable