package com.ezdream.project.domain.coin

import com.ezdream.project.network.UseCase
import com.ezdream.project.repository.coinService.CoinRepository
import com.ezdream.project.repository.coinService.reqres.PriceModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PriceByIdUseCase @Inject constructor(private val repository: CoinRepository) :
    UseCase<String, Response<List<PriceModel>>>() {

    override fun execute(parameter: String?): Flow<Response<List<PriceModel>>> {
        return repository.getPriceById(parameter)
    }

}