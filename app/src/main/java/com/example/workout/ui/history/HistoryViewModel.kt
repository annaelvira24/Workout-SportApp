package com.example.workout.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workout.retrofitclient.News

class HistoryViewModel : ViewModel() {

//    private var newsListView: MutableLiveData<ArrayList<News>>? = null

//    internal fun getNewsList(): MutableLiveData<ArrayList<News>> {
//        if (fruitList == null) {
//            fruitList = MutableLiveData()
//            loadFruits()
//        }
//        return fruitList as MutableLiveData<List<Model>>
//    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is training history Fragment"
    }
    val text: LiveData<String> = _text
}