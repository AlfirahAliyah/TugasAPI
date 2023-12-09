package com.dicoding.mygithub2.ui.page2

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mygithub2.api.ApiClient
import com.dicoding.mygithub2.data.local.FavAkun
import com.dicoding.mygithub2.data.local.FavAkunDao
import com.dicoding.mygithub2.data.local.LocalDatabase
import com.dicoding.mygithub2.data.respone.DetailResponse
import com.dicoding.mygithub2.data.respone.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application): AndroidViewModel(application) {

    val user = MutableLiveData<DetailResponse>()
    val userFollower = MutableLiveData<List<User>>()
    val userFollowing = MutableLiveData<List<User>>()

    private var userDao: FavAkunDao?
    private var userDb: LocalDatabase?

    init {
        userDb = LocalDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetail(username: String){
        _isLoading.value = true
        val client = ApiClient.getApi().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {

            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun cariDetail(): LiveData<DetailResponse>{
        return user
    }

    fun getUserFollower(username: String) {
        _isLoading.value = true
        val client = ApiClient.getApi().getFollowers(username)
        client.enqueue(object : Callback<List<User>>{
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
//
                    userFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun cariFollower(): LiveData<List<User>>{
        return userFollower
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiClient.getApi().getFollowing(username)
        client.enqueue(object : Callback<List<User>>{
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
//
                    userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun cariFollowing(): LiveData<List<User>>{
        return userFollowing
    }

    fun addFavorite(username: String, id:Long, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavAkun(
                username, id, avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }

    fun checkAkun(id: Long) = userDao?.checkAkun(id)

    fun removeFavorite(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }

    companion object{
        val TAG = "DetailViewModel"
    }

}

