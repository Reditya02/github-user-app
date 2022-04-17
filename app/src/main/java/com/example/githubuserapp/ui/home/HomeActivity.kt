package com.example.githubuserapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityHomeBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.githubuserapp.R
import com.example.githubuserapp.model.*
import com.example.githubuserapp.ui.favorite.FavoriteActivity
import com.example.githubuserapp.ui.setting.SettingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListUser.setHasFixedSize(true)

        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val query = p0.toString().filter { !it.isWhitespace() }
                viewModel.searchUser(query)
                val observer = Observer<ListResponse> {
                    showRecycler(it)
                }
                viewModel.list.observe(this@HomeActivity, observer)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showRecycler(list: ListResponse) {
        binding.rvListUser.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(list)
        binding.rvListUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}
