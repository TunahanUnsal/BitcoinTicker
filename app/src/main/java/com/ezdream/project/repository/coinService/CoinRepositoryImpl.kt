package com.ezdream.project.repository.coinService

import com.ezdream.project.network.ApiRepository
import com.ezdream.project.repository.coinService.reqres.Coin
import com.ezdream.project.repository.coinService.reqres.CoinDetail
import com.ezdream.project.repository.coinService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val service: CoinService) : ApiRepository(),
    CoinRepository {

    override fun getCoinList(): Flow<Response<List<Coin>>> = sendRequest { service.getCoinList() }

    override fun getCoinById(id: String?): Flow<CoinDetail> =
        sendRequest { service.getCoinById(id) }

    override fun getPriceById(id: String?): Flow<Response<List<PriceModel>>> =
        sendRequest { service.getCoinPriceById(id) }

}