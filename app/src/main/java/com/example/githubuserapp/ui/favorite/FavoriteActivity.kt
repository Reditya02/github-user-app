package com.example.githubuserapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.model.locale.User
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.setHasFixedSize(true)

        viewModel.getUserFavorite().observe(this) {
            val list = viewList(it)
            if (list.isNotEmpty()) {
                binding.emptyText.visibility = View.GONE
            }
            showRecycler(list)
        }
    }

    private fun viewList(users: List<User>): List<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val viewListUser = User(
                user.avatar_url,
                user.id,
                user.login
            )
            listUsers.add(viewListUser)
        }
        return listUsers
    }

    private fun showRecycler(list: List<User>) {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        val adapter = FavoriteAdapter(list)
        binding.rvFavorite.adapter = adapter
    }
}