package com.example.project.ui.home

import androidx.lifecycle.ViewModel
import com.example.project.BuildConfig
import com.example.project.domain.coin.CoinByIdUseCase
import com.example.project.domain.coin.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityVM @Inject constructor(
    private val coinListUseCase: CoinListUseCase,
    private val coinByIdUseCase: CoinByIdUseCase
) : ViewModel() {

    fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }
}