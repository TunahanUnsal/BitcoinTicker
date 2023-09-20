package com.example.project.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project.R
import com.example.project.databinding.ActivityLoginBinding
import com.example.project.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("TAG", "onCreate: LoginActivity")

        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginActivityVM::class.java]
        setContentView(binding.root)


        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this,HomeActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            },
            5000 // value in milliseconds
        )

    }
}