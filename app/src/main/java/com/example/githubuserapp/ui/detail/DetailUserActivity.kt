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
            val company = " : ${it.company ?: "-"}"
            val location = " : ${it.location ?: "-"}"

            Glide.with(this)
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)

            binding.apply {
                tvUsername.text = it.login
                tvName.text = it.name
                tvRepositories.text = it.publicRepos.toString()
                followers.text = it.followers.toString()
                following.text = it.following.toString()
                tvCompany.text = company
                tvLocation.text = location
            }

            user = User(
                it?.avatarUrl,
                it?.id,
                it?.login
            )

            CoroutineScope(Dispatchers.IO).launch {
                val count = user.id?.let { id -> viewModel.checkUserFavorite(id) }
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

        viewModel.apply {
            userDetail.observe(this@DetailUserActivity, observerProfile)
            message.observe(this@DetailUserActivity) {
                makeToast(it)
            }
            showFollowers(username)
            showFollowing(username)
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite == 1) {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_not_favorite
                    )
                )
                viewModel.removeFavoriteUser(user.id!!)

                makeToast("User telah dihapus dari daftar Favorite")
                isFavorite = 0
            } else {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_favorite
                    )
                )
                viewModel.addToFavoriteUser(user)

                makeToast("Sukses Menambahkan kedaftar Favorite")

                isFavorite = 1
            }
        }

        viewModel.isDetailLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(isLoading: Boolean) {

        var loadingVisibility: Int = View.GONE
        var componentVisibility: Int = View.VISIBLE

        if (isLoading) {
            loadingVisibility = View.VISIBLE
            componentVisibility = View.GONE
        }

        binding.apply {
            progressBar.visibility = loadingVisibility
            btnFavorite.visibility = componentVisibility
            tvUsername.visibility = componentVisibility
            tvName.visibility = componentVisibility
            tableCompanyLocation.visibility = componentVisibility
            tableRepositoryFollowerFollowing.visibility = componentVisibility
            tabs.visibility = componentVisibility
            viewPager.visibility = componentVisibility
        }
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