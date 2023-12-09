package com.dicoding.mygithub2.ui.utama

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mygithub2.api.ApiClient
import com.dicoding.mygithub2.data.respone.AkunResponse

import com.dicoding.mygithub2.data.respone.User
import com.dicoding.mygithub2.settings.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AkunViewModel(private val pref: SettingPreferences) : ViewModel() {

    val listAkun = MutableLiveData<ArrayList<User>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUser(query: String){
        _isLoading.value = true
        val client = ApiClient.getApi().getSearchAkun(query)
        client.enqueue(object : Callback<AkunResponse> {
            override fun onResponse(
                call: Call<AkunResponse>,
                response: Response<AkunResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    listAkun.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AkunResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun cariUser(): LiveData<ArrayList<User>>{
        return listAkun
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    companion object{
        val TAG = "AkunViewModel"
    }
}