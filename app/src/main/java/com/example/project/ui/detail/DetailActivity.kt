package com.example.project.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[DetailActivityVM::class.java]
        setContentView(binding.root)

        val name: String = intent.getStringExtra("name").toString()


        CoroutineScope(Dispatchers.IO).launch {
            val response = async {
                viewModel.coinDetailFun(
                    this@DetailActivity,
                    applicationContext,
                    name,
                    binding.algorithm,
                    binding.description,
                    binding.imageView
                )
                viewModel.priceDetailFun(
                    this@DetailActivity,
                    name,
                    binding.price,
                    binding.priceChange
                )
            }
            response.await()
        }

    }
}