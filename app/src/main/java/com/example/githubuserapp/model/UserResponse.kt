package com.example.githubuserapp.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("id")
	@ColumnInfo(name = "id")
	@PrimaryKey
	val id: Int? = null,

	@field:SerializedName("login")
	@ColumnInfo(name = "login")
	val login: String? = null,

	@field:SerializedName("name")
	@ColumnInfo(name = "name")
	val name: String? = null,

	@field:SerializedName("avatar_url")
	@ColumnInfo(name = "avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following_url")
	@ColumnInfo(name = "following_url")
	val followingUrl: String? = null,

	@field:SerializedName("following")
	@ColumnInfo(name = "following")
	val following: Int? = null,

	@field:SerializedName("followers_url")
	@ColumnInfo(name = "followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("public_repos")
	@ColumnInfo(name = "public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("followers")
	@ColumnInfo(name = "followers")
	val followers: Int? = null,

	@field:SerializedName("company")
	@ColumnInfo(name = "company")
	val company: String? = null,

	@field:SerializedName("location")
	@ColumnInfo(name = "location")
	val location: String? = null,

	@ColumnInfo(name = "isFavorite")
	var isFavorite: Boolean? = false

)

data class ListResponse(
	@field:SerializedName("items")
	val listUser: ArrayList<UserResponse>
)
