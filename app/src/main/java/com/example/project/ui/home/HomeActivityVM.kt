package com.example.project.ui.home

import androidx.lifecycle.ViewModel
import com.example.project.BuildConfig
import com.example.project.domain.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityVM @Inject constructor() : ViewModel() {

    fun getAppVersion(): String{
        return BuildConfig.VERSION_NAME
    }

}