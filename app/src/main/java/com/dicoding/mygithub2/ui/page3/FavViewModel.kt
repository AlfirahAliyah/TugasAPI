package com.dicoding.mygithub2.ui.page3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.mygithub2.data.local.FavAkun
import com.dicoding.mygithub2.data.local.FavAkunDao
import com.dicoding.mygithub2.data.local.LocalDatabase

class FavViewModel (application: Application): AndroidViewModel(application)  {

    private var userDao: FavAkunDao?
    private var userDb: LocalDatabase?

    init {
        userDb = LocalDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()

    }

    fun getFavAkun() : LiveData<List<FavAkun>>? {
        return userDao?.getFavAkun()
    }
}