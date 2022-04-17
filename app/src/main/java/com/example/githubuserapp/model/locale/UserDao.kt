package com.example.githubuserapp.model.locale

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.model.UserResponse

@Dao
interface UserDao {

    @Query("select * from user order by username asc")
    fun showFavoriteListUser(): LiveData<List<User>>

    @Query("select * from user where username = :username")
    suspend fun showDetailFavoriteUser(username: String): UserResponse?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(user: User)

    @Query("delete from user where id = :id")
    suspend fun removeFavoriteUser(id: Int)

    @Query("select count(*) from user where id = :id")
    suspend fun checkUser(id: Int): Int

}