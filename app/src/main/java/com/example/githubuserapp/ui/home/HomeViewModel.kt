package com.example.githubuserapp.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.Const.TAG
import com.example.githubuserapp.model.remote.ApiConfig
import com.example.githubuserapp.model.ListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _list = MutableLiveData<ListResponse>()
    var list: LiveData<ListResponse> = _list

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(query: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<ListResponse> {
            override fun onResponse(
                call: Call<ListResponse>,
                response: Response<ListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _list.setValue(responseBody!!)
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                Log.d(TAG, "Tidak dapat menampilkan hasil pencarian")
            }
        })
    }
}