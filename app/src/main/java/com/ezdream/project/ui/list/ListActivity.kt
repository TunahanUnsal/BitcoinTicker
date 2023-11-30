package com.ezdream.project.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ezdream.project.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ListActivityVM::class.java]
        setContentView(binding.root)

        viewModel.setupUI(binding.viewGeneral, this@ListActivity)

        viewModel.search(binding.searchEditText)

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.loadingFlag = true
            val response = async {
                viewModel.coinListFun(binding.coinListView, this@ListActivity)
            }
            response.await()
            viewModel.loadingFlag = false
        }

        binding.swipeRefresh.setOnRefreshListener {
            if(!viewModel.loadingFlag){
                viewModel.loadingFlag = true
                binding.searchEditText.text.clear()
                if(!viewModel.fabMode){
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = async {
                            viewModel.coinListFun(binding.coinListView, this@ListActivity)
                        }
                        response.await()
                        runOnUiThread {
                            binding.swipeRefresh.isRefreshing = false
                        }
                        viewModel.loadingFlag = false
                    }
                }else{
                    runOnUiThread {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    viewModel.funGetFavoritesFromServer()
                    viewModel.loadingFlag = false
                }

            }
        }

        binding.fab.setOnClickListener {
            viewModel.fabClicked(this@ListActivity,binding.root,binding.fab)
        }

    }

    override fun onStart() {
        super.onStart()
        /*if(!viewModel.fabMode){
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.loadingFlag = true
                val response = async {
                    viewModel.coinListFun(binding.coinListView, this@ListActivity)
                }
                response.await()
                viewModel.loadingFlag = false
            }
        }else{
            viewModel.funGetFavoritesFromServer()
        }*/

    }

}