package com.example.project.ui.list

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
import com.example.project.domain.coin.CoinListUseCase
import com.example.project.repository.coinService.reqres.Coin
import com.example.project.ui.adapter.CoinListAdapter
import com.example.project.util.CustomItemAnimator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ListActivityVM @Inject constructor(private val coinListUseCase: CoinListUseCase) :
    ViewModel() {

    private lateinit var coinListAdapter: CoinListAdapter
    private var coinList: ArrayList<Coin> = ArrayList()
    private var tempCoinList: ArrayList<Coin> = ArrayList()

    var loadingFlag = false

    suspend fun coinListFun(recyclerView: RecyclerView, activity: Activity) {
        coinListUseCase.invoke(
            parameter = null
        ).onStart {
            Log.i("TAG", "loginFun: onStart")
            loadingFlag = true
        }.catch {
            Log.i("TAG", "loginFun: catch $it")
            loadingFlag = false
        }.collect {
            loadingFlag = false
            Log.i("TAG", "loginFun: collect ${it.body()}")
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
            coinListAdapter = CoinListAdapter(tempCoinList)
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
                if(!loadingFlag){
                    searchItems(searchText)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchItems(query: String) {

        tempCoinList.clear()
        tempCoinList.addAll(coinList.filter { it.name?.lowercase(Locale.ROOT)?.contains(query) ?: false})
        coinListAdapter.notifyDataSetChanged()

    }

}