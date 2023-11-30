package com.ezdream.project.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ezdream.project.R
import com.ezdream.project.databinding.ActivityLoginBinding
import com.ezdream.project.ui.home.HomeActivity
import com.ezdream.project.ui.list.ListActivity
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
                startActivity(Intent(this,ListActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            },
            5000 // value in milliseconds
        )

    }
}