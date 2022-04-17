package com.example.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.model.locale.User
import com.example.githubuserapp.model.locale.UserDao
import com.example.githubuserapp.model.locale.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var database: UserDatabase? = UserDatabase.getInstance(application)
    private var userDao: UserDao? = database?.userDao()

    fun getUserFavorite(): LiveData<List<User>> {
        return userDao?.showFavoriteListUser()!!
    }
}