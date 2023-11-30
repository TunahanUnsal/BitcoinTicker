package com.ezdream.project.ui.detail

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
import com.ezdream.project.R
import com.ezdream.project.domain.coin.CoinByIdUseCase
import com.ezdream.project.domain.coin.PriceByIdUseCase
import com.ezdream.project.repository.coinService.reqres.CoinDetail
import com.ezdream.project.repository.coinService.reqres.PriceModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class DetailActivityVM @Inject constructor(
    private val coinByIdUseCase: CoinByIdUseCase,
    private val priceByIdUseCase: PriceByIdUseCase
) : ViewModel() {

    var loadingFlag: Boolean = false
    private var favClicked: Boolean = false

    suspend fun coinDetailFun(
        activity: Activity,
        context: Context,
        id: String,
        algorithm: TextView,
        description: TextView,
        imageView: ImageView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        coinByIdUseCase.invoke(
            parameter = id
        ).onStart {
            Log.i("TAG", "coinDetailFun: onStart")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = true
            }
        }.catch {
            Log.i("TAG", "coinDetailFun: catch $it")
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
        }.collect {
            activity.runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
            }
            Log.i("TAG", "coinDetailFun: collect $it")
            uiSetterCoin(activity, it, algorithm, description)
            if (it.logo != null) {
                imageSetter(it.logo!!, imageView, context, activity)
            } else {
                imageSetter("", imageView, context, activity)
            }
        }
    }

    suspend fun priceDetailFun(
        activity: Activity,
        id: String,
        price: TextView,
        change: TextView
    ) {
        priceByIdUseCase.invoke(
            parameter = id
        ).onStart {
            Log.i("TAG", "coinDetailFun: onStart")
        }.catch {
            Log.i("TAG", "coinDetailFun: catch $it")
        }.collect {
            Log.i("TAG", "coinDetailFun: collect $it")
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
            price.text = "%.2f".format(priceDetail.close) + "$"
            if (priceDetail.high != null && priceDetail.low != null) {
                change.text = "%.2f".format(priceDetail.high!!.minus(priceDetail.low!!)) + "$"
            }
        }
    }

    private fun imageSetter(
        url: String,
        imageView: ImageView,
        context: Context,
        activity: Activity
    ) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .error(R.drawable.binance)

        activity.runOnUiThread {
            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }

    fun favClicked(
        activity: Activity,
        imageView: ImageView,
        name: String,
        symbol: String,
        rank: String,
        type: String,
        id: String
    ) {
        imageView.setOnClickListener {
            if (!favClicked) {
                favClicked = true
                activity.runOnUiThread {
                    imageView.setImageResource(R.drawable.favorite_on)
                }
                addFileStore(id,name, symbol, rank, type)
            } else {
                favClicked = false
                activity.runOnUiThread {
                    imageView.setImageResource(R.drawable.favorite_off)
                }
                removeFileStore(name)
            }
        }
    }

    fun funControlFireStore(name: String, activity: Activity, imageView: ImageView) {
        try {
            val fireStoreDatabase = FirebaseFirestore.getInstance()
            fireStoreDatabase.collection(FirebaseAuth.getInstance().uid!!).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.get("name")?.equals(name) == true) {
                            favClicked = true
                            activity.runOnUiThread {
                                imageView.setImageResource(R.drawable.favorite_on)
                            }
                        }
                    }

                }
        }catch (e : Exception){
            Log.i("TAG", "funControlFireStore: $e")
        }


    }

    private fun addFileStore(id: String, name: String, symbol: String, rank: String, type: String) {

        try {
            val fireStoreDatabase = FirebaseFirestore.getInstance()

            val hashMap = hashMapOf<String, Any>(
                "id" to id,
                "name" to name,
                "symbol" to symbol,
                "rank" to rank,
                "type" to type
            )

            fireStoreDatabase.collection(FirebaseAuth.getInstance().uid!!)
                .add(hashMap)
                .addOnSuccessListener {
                    Log.d("TAG", "Added document with ID ${it.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error adding document $exception")
                }
        }catch (e:Exception){
            Log.i("TAG", "addFileStore: $e")
        }



    }

    private fun removeFileStore(name: String) {
        try {
            val fireStoreDatabase = FirebaseFirestore.getInstance()
            if (FirebaseAuth.getInstance().uid != null) {
                val itemsRef: CollectionReference =
                    fireStoreDatabase.collection(FirebaseAuth.getInstance().uid!!)
                val query: Query = itemsRef.whereEqualTo("name", name)
                query.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            itemsRef.document(document.id).delete().addOnSuccessListener {
                                Log.d("TAG", "Removed document")
                            }.addOnFailureListener {
                                Log.d("TAG", "Failed")
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.exception)
                    }
                }
            }
        }catch (e : Exception){
            Log.i("TAG", "removeFileStore: $e")
        }
    }
}