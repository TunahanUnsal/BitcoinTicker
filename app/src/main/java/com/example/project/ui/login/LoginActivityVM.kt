package com.example.project.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG: String = "LoginActivityVM"

@HiltViewModel
class LoginActivityVM @Inject constructor() : ViewModel() {

   /* private suspend fun loginFun() {
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
    }*/
}