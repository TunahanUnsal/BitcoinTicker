package com.example.project.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.project.domain.coin.CoinByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailActivityVM @Inject constructor(private val coinByIdUseCase: CoinByIdUseCase) : ViewModel() {

    suspend fun coinDetailFun() {
        coinByIdUseCase.invoke(
            parameter = "btc-bitcoin"
        ).onStart {
            Log.i("TAG", "loginFun: onStart")
        }.catch {
            Log.i("TAG", "loginFun: catch $it")
        }.collect {
            Log.i("TAG", "loginFun: collect ${it}")
        }
    }
}