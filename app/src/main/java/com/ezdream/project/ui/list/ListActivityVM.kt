package com.ezdream.project.ui.list

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezdream.project.R
import com.ezdream.project.domain.coin.CoinListUseCase
import com.ezdream.project.repository.coinService.reqres.Coin
import com.ezdream.project.ui.adapter.CoinListAdapter
import com.ezdream.project.util.CustomItemAnimator
import com.ezdream.project.util.UiUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import java.util.Locale
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
@HiltViewModel
class ListActivityVM @Inject constructor(private val coinListUseCase: CoinListUseCase) :
    ViewModel() {

    private lateinit var coinListAdapter: CoinListAdapter
    private var coinList: ArrayList<Coin> = ArrayList()
    private var tempCoinList: ArrayList<Coin> = ArrayList()
    private var favCoinList: ArrayList<Coin> = ArrayList()

    var loadingFlag = false
    var fabMode = false

    suspend fun coinListFun(recyclerView: RecyclerView, activity: Activity) {
        coinListUseCase.invoke(
            parameter = null
        ).onStart {
            Log.i("TAG", "coinListFun: onStart")
            loadingFlag = true
        }.catch {
            Log.i("TAG", "coinListFun: catch $it")
            loadingFlag = false
        }.collect {
            loadingFlag = false
            Log.i("TAG", "coinListFun: collect ${it.body()}")
            val list: ArrayList<Coin>? = it.body() as ArrayList<Coin>?
            Log.i("TAG", "coinListFun: ${list?.get(0)?.id}")
            coinList.clear()
            tempCoinList.clear()
            (it.body() as ArrayList<Coin>?)?.let { it1 -> coinList.addAll(it1) }
            (it.body() as ArrayList<Coin>?)?.let { it1 -> tempCoinList.addAll(it1) }
            Log.i("TAG", "$coinList")
            setList(recyclerView, activity)
        }
    }

    private fun setList(recyclerView: RecyclerView, activity: Activity) {
        activity.runOnUiThread {
            coinListAdapter = CoinListAdapter(tempCoinList, activity)
            recyclerView.layoutManager = LinearLayoutManager(activity);
            recyclerView.post {
                recyclerView.adapter = coinListAdapter
                recyclerView.itemAnimator = CustomItemAnimator()
            }

        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View, activity: Activity) {

        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(activity)
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView, activity)
            }
        }
    }

    fun search(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim().lowercase(Locale.ROOT)
                if (!loadingFlag) {
                    searchItems(searchText)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchItems(query: String) {

        tempCoinList.clear()
        if (!fabMode) {
            tempCoinList.addAll(coinList.filter {
                it.name?.lowercase(Locale.ROOT)?.contains(query) ?: false
            })
        } else {
            tempCoinList.addAll(favCoinList.filter {
                it.name?.lowercase(Locale.ROOT)?.contains(query) ?: false
            })
        }

        coinListAdapter.notifyDataSetChanged()

    }

    fun fabClicked(
        activity: Activity,
        parentView: View,
        floatingActionButton: FloatingActionButton,
    ) {
        floatingActionButton.setOnClickListener {
            if (!fabMode) {
                fabMode = true
                funGetFavoritesFromServer()
                activity.runOnUiThread {
                    floatingActionButton.setImageResource(R.drawable.favorite_on)
                    UiUtil.showSnackBar(
                        parentView, activity.getString(R.string.fav_active)
                    )
                }
            } else {
                fabMode = false
                favModDeactivator()
                activity.runOnUiThread {
                    floatingActionButton.setImageResource(R.drawable.favorite_off)
                    UiUtil.showSnackBar(
                        parentView, activity.getString(R.string.fav_closed)
                    )
                }
            }

        }
    }

    fun funGetFavoritesFromServer() {
        favCoinList.clear()
        try {
            val fireStoreDatabase = FirebaseFirestore.getInstance()
            if (FirebaseAuth.getInstance().uid != null) {
                fireStoreDatabase.collection(FirebaseAuth.getInstance().uid!!).get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            favCoinList.add(
                                Coin(
                                    id = document.get("id").toString(),
                                    name = document.get("name").toString(),
                                    symbol = document.get("symbol").toString(),
                                    rank = document.get("rank").toString().toInt(),
                                    type = document.get("type").toString()
                                )
                            )
                        }
                        favModActivator()

                    }
            }
        }catch (e : Exception){
            Log.i("TAG", "funGetFavoritesFromServer: $e")
            favModActivator()
        }

    }


    private fun favModActivator() {
        tempCoinList.clear()
        tempCoinList.addAll(favCoinList)
        coinListAdapter.notifyDataSetChanged()
    }

    private fun favModDeactivator() {
        tempCoinList.clear()
        tempCoinList.addAll(coinList)
        coinListAdapter.notifyDataSetChanged()
    }


}