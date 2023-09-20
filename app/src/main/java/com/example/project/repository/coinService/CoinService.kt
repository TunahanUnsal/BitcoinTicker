package com.example.project.repository.coinService

import com.example.project.repository.coinService.reqres.Coin
import com.example.project.repository.coinService.reqres.CoinDetail
import com.example.project.repository.coinService.reqres.PriceModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {

    @GET("v1/coins")
    suspend fun  getCoinList(): Response<List<Coin>>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path(value = "coinId") coinId : String?): CoinDetail

    @GET("v1/coins/{coinId}/ohlcv/latest")
    suspend fun getCoinPriceById(@Path(value = "coinId") coinId : String?): Response<List<PriceModel>>
}