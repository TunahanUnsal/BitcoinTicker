package com.ezdream.project.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ezdream.project.databinding.ActivityDetailBinding
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
        val id: String = intent.getStringExtra("id").toString()
        val type: String = intent.getStringExtra("type").toString()
        val rank: String = intent.getStringExtra("rank").toString()
        val symbol: String = intent.getStringExtra("symbol").toString()

        viewModel.favClicked(this@DetailActivity,binding.fav,name,symbol, rank, type,id)

        viewModel.funControlFireStore(name,this@DetailActivity,binding.fav)

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.loadingFlag = true
            val response = async {
                viewModel.coinDetailFun(
                    this@DetailActivity,
                    applicationContext,
                    id,
                    binding.algorithm,
                    binding.description,
                    binding.imageView,
                    binding.swipeRefresh
                )
                viewModel.priceDetailFun(
                    this@DetailActivity,
                    id,
                    binding.price,
                    binding.priceChange
                )
            }
            response.await()
            runOnUiThread {
                binding.swipeRefresh.isRefreshing = false
            }
            viewModel.loadingFlag = false
        }

        binding.swipeRefresh.setOnRefreshListener {
            if(!viewModel.loadingFlag){
                viewModel.loadingFlag = true
                CoroutineScope(Dispatchers.IO).launch {
                    val response = async {
                        viewModel.coinDetailFun(
                            this@DetailActivity,
                            applicationContext,
                            id,
                            binding.algorithm,
                            binding.description,
                            binding.imageView,
                            binding.swipeRefresh
                        )
                        viewModel.priceDetailFun(
                            this@DetailActivity,
                            id,
                            binding.price,
                            binding.priceChange
                        )
                    }
                    response.await()
                    runOnUiThread {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    viewModel.loadingFlag = false
                }
            }
        }
    }

}