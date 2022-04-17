package com.example.githubuserapp.model.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
class User (
    @ColumnInfo(name = "avatarUrl")
    val avatar_url: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "username")
    val login: String? = null
) : Serializable