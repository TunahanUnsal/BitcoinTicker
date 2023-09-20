package com.example.project.repository.coinService

import com.example.project.network.ApiRepository
import com.example.project.repository.coinService.reqres.Coin
import com.example.project.repository.coinService.reqres.CoinDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val service: CoinService) : ApiRepository(),
    CoinRepository {

    override fun getCoinList(): Flow<Response<List<Coin>>> = sendRequest { service.getCoinList() }

    override fun getCoinById(id: String?): Flow<CoinDetail> =
        sendRequest { service.getCoinById(id) }

}