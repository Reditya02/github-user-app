package com.example.githubuserapp.model.remote

import com.example.githubuserapp.model.ListResponse
import com.example.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers (
        @Query("q")
        query: String
    ): Call<ListResponse>

    @GET("users/{username}")
    fun getDetailUser (
        @Path("username")
        username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username")
        username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing (
        @Path("username")
        username: String
    ): Call<ArrayList<UserResponse>>
}