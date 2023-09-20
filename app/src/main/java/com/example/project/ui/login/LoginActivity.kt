package com.example.project.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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

        Glide
            .with(this)
            .asGif()
            .load(R.drawable.gif_splash)
            .into(binding.giff)


        Handler(Looper.getMainLooper()).postDelayed(
            {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@LoginActivity, binding.giff, ViewCompat.getTransitionName(binding.giff)!!
                )
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java).setAction(""),options.toBundle())
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            },
            3500 // value in milliseconds
        )

    }
}