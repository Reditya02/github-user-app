package com.example.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.githubuserapp.Const.TAG
import com.example.githubuserapp.model.remote.ApiConfig
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.model.locale.User
import com.example.githubuserapp.model.locale.UserDao
import com.example.githubuserapp.model.locale.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> = _userDetail

    private val _listFollowers = MutableLiveData<ArrayList<UserResponse>>()
    val listFollowers: LiveData<ArrayList<UserResponse>> = _listFollowers

    private val _listFollowing = MutableLiveData<ArrayList<UserResponse>>()
    val listFollowing: LiveData<ArrayList<UserResponse>> = _listFollowing

    private val _isDetailLoading = MutableLiveData<Boolean>()
    val isDetailLoading: LiveData<Boolean> = _isDetailLoading

    private val _isFollowLoading = MutableLiveData<Boolean>()
    val isFollowLoading: LiveData<Boolean> = _isFollowLoading

    private var database: UserDatabase? = UserDatabase.getInstance(application)
    private var userDao: UserDao? = database?.userDao()

    fun showDetailUser(username : String) {
        _isDetailLoading.postValue(true)
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserResponse> {

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isDetailLoading.postValue(false)
                val responseBody = response.body()
                if (responseBody != null) {
                    _userDetail.postValue(responseBody!!)
                } else {
                    Log.d(TAG, "user null")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d(TAG, "Tidak dapat menampilkan detail user")
            }
        })
    }

    fun showFollowers(username : String) {
        _isFollowLoading.postValue(true)
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(call: Call<ArrayList<UserResponse>>, response: Response<ArrayList<UserResponse>>) {
                if (response.isSuccessful) {
                    _isFollowLoading.postValue(false)

                    val responseBody = response.body()

                    if (responseBody != null) {
                        _listFollowers.postValue(response.body())
                        Log.d(TAG, "SearchResultAAAA " + response.body().toString())
                        Log.d(TAG, "LiveDataAAA " + listFollowers.value.toString())

                    } else {
                        Log.d(TAG, "followers result null")
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }

    fun showFollowing(username : String) {
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(call: Call<ArrayList<UserResponse>>, response: Response<ArrayList<UserResponse>>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    //val responseBody = Gson().fromJson<Response<ListResponse>>(response.body(), ListResponse::class.java)
                    if (responseBody != null) {
                        _listFollowing.postValue(response.body())
                        Log.d(TAG, "SearchResultAAAA " + response.body().toString())
                        Log.d(TAG, "LiveDataAAA " + listFollowers.value.toString())

                    } else {
                        Log.d(TAG, "followers result null")
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addToFavoriteUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.addFavoriteUser(user)
        }
    }

    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavoriteUser(id)
        }
    }

    suspend fun checkUserFavorite(id: Int) = userDao?.checkUser(id)
}
