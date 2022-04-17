package com.example.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityUserProfileBinding
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.model.locale.User
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val viewModel: DetailUserViewModel by viewModels()
    private lateinit var user: User
    private var isFavorite: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val username = intent.getStringExtra(EXTRA_USER).toString()
        viewModel.showDetailUser(username)

        val observerProfile = Observer<UserResponse> {
            val company = " : ${it.company}"
            val location = " : ${it.location}"

            Glide.with(this)
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)

            binding.tvUsername.text = it.login
            binding.tvName.text = it.name
            binding.tvRepositories.text = it.publicRepos.toString()
            binding.followers.text = it.followers.toString()
            binding.following.text = it.following.toString()
            binding.tvCompany.text = company
            binding.tvLocation.text = location

            user = User(
                it?.avatarUrl,
                it?.id,
                it?.login
            )

            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUserFavorite(user.id!!)
                withContext(Dispatchers.Main) {
                    if (count == 1) {
                        binding.btnFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.ic_favorite
                            )
                        )
                        viewModel.addToFavoriteUser(user)
                        isFavorite = 1
                    }
                }
            }
        }
        viewModel.userDetail.observe(this, observerProfile)
        viewModel.showFollowers(username)
        viewModel.showFollowing(username)

        binding.btnFavorite.setOnClickListener {
            if (isFavorite == 1) {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_not_favorite
                    )
                )
                viewModel.removeFavoriteUser(user.id!!)

                Toast.makeText(
                    this,
                    "User telah dihapus dari daftar Favorite",
                    Toast.LENGTH_SHORT
                ).show()
                isFavorite = 0
            } else {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite
                    )
                )
                if (username != null) {
                    viewModel.addToFavoriteUser(user)
                }


                Toast.makeText(
                    this,
                    "Sukses Menambahkan kedaftar Favorite",
                    Toast.LENGTH_SHORT
                ).show()

                isFavorite = 1
            }
        }

        viewModel.isDetailLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnFavorite.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvUsername.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvName.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tableCompanyLocation.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tableRepositoryFollowerFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tabs.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.viewPager.visibility = if (isLoading) View.GONE else View.VISIBLE


    }

    companion object {

        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}