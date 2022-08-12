package com.example.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.helper.Message
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

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

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
                        responseBody.let {
                            _list.value = it
                        }
                    } else {
                        _message.value = Message.SEARCH_NOT_FOUND
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                _message.value = Message.SEARCH_NOT_FOUND
            }
        })
    }
}