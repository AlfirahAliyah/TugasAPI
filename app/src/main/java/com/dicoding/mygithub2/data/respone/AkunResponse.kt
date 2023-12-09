package com.dicoding.mygithub2.data.respone

import com.google.gson.annotations.SerializedName

data class AkunResponse(
    @field:SerializedName("items")
    val items: ArrayList<User>
)

data class User(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)

data class DetailResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("name")
    val nama: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int

)

