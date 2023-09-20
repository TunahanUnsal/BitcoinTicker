package com.example.project.ui.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.project.R
import com.example.project.domain.coin.CoinByIdUseCase
import com.example.project.domain.coin.PriceByIdUseCase
import com.example.project.repository.coinService.reqres.CoinDetail
import com.example.project.repository.coinService.reqres.PriceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class DetailActivityVM @Inject constructor(
    private val coinByIdUseCase: CoinByIdUseCase,
    private val priceByIdUseCase: PriceByIdUseCase
) : ViewModel() {

    suspend fun coinDetailFun(
        activity: Activity,
        context: Context,
        name: String,
        algorithm: TextView,
        description: TextView,
        imageView: ImageView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        coinByIdUseCase.invoke(
            parameter = name
        ).onStart {
            Log.i("TAG", "loginFun: onStart")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = true
            }
        }.catch {
            Log.i("TAG", "loginFun: catch $it")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
        }.collect {
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
            Log.i("TAG", "loginFun: collect $it")
            uiSetterCoin(activity, it, algorithm, description)
            if(it.logo!=null){
                imageSetter(it.logo!!,imageView,context,activity)
            }else{
                imageSetter("",imageView,context,activity)
            }
        }
    }

    suspend fun priceDetailFun(
        activity: Activity,
        name: String,
        price: TextView,
        change: TextView
    ) {
        priceByIdUseCase.invoke(
            parameter = name
        ).onStart {
            Log.i("TAG", "loginFun: onStart")
        }.catch {
            Log.i("TAG", "loginFun: catch $it")
        }.collect {
            Log.i("TAG", "loginFun: collect $it")
            it.body()?.get(0)?.let { it1 -> uiSetterPrice(activity, it1, price, change) }
        }
    }

    private fun uiSetterCoin(
        activity: Activity,
        coinDetail: CoinDetail,
        algorithm: TextView,
        description: TextView
    ) {
        activity.runOnUiThread {
            algorithm.text = coinDetail.hashAlgorithm
            description.text = coinDetail.description
        }
    }

    @SuppressLint("SetTextI18n")
    private fun uiSetterPrice(
        activity: Activity,
        priceDetail: PriceModel,
        price: TextView,
        change: TextView
    ) {
        activity.runOnUiThread {
            price.text =  "%.2f".format( priceDetail.close) + "$"
            if (priceDetail.high != null && priceDetail.low != null) {
                change.text = "%.2f".format(priceDetail.high!!.minus(priceDetail.low!!)) + "$"
            }
        }
    }

    private fun imageSetter(url: String, imageView: ImageView, context: Context,activity: Activity) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .error(R.drawable.binance)

        activity.runOnUiThread {
            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }
}