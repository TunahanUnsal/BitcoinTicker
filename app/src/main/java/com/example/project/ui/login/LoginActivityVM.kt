package com.example.project.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.project.domain.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val TAG: String = "LoginActivityVM"

@HiltViewModel
class LoginActivityVM @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private suspend fun loginFun() {
        loginUseCase.invoke(
            LoginUseCase.Params(
                username = "",
                password = ""
            )
        ).onStart {
            Log.i(TAG, "loginFun: onStart")
        }.catch {
            Log.i(TAG, "loginFun: catch $it")
        }.collect {
            Log.i(TAG, "loginFun: collect $it")
        }
    }

}