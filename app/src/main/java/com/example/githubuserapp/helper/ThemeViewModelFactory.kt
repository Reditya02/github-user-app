package com.example.githubuserapp.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubuserapp.model.locale.SettingPreferences
import com.example.githubuserapp.ui.setting.SettingViewModel
import com.example.githubuserapp.ui.splashscreen.SplashScreenViewModel

class ThemeViewModelFactory(private val pref: SettingPreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        else if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(pref) as T
        }
        throw IllegalArgumentException("ViewModel Class not found")
    }
}