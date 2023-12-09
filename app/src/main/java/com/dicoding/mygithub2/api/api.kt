package com.dicoding.mygithub2.api

import com.dicoding.mygithub2.data.respone.AkunResponse
import com.dicoding.mygithub2.data.respone.DetailResponse
import com.dicoding.mygithub2.data.respone.User
import retrofit2.Call
import retrofit2.http.*

interface api {
    @GET("search/users")
    @Headers( "Authorization: token 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")
    fun getSearchAkun(
        @Query("q") query: String
    ): Call<AkunResponse>

    @GET("users/{username}")
    @Headers( "Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers( "Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers( "Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}