package com.example.githubuserapp.ui.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.Const.TIME_SPLASH
import com.example.githubuserapp.R
import com.example.githubuserapp.model.locale.SettingPreferences
import com.example.githubuserapp.ui.home.HomeActivity
import com.example.githubuserapp.viewModel.ThemeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = SettingPreferences.getInstance(dataStore)

        val splashScreenViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            SplashScreenViewModel::class.java
        )

        Handler(mainLooper).postDelayed({
            splashScreenViewModel.getThemeSettings().observe(this) {
                if (it) {
                    intentToHome()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    intentToHome()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }

    fun intentToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

}