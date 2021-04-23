package com.example.workout.ui.news

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workout.retrofitclient.News

class NewsViewModel : ViewModel() {

    private val _allNews = MutableLiveData<List<News>>()
        val allNews : LiveData<List<News>> = _allNews

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
//    fun insert(word: Word) = viewModelScope.launch {
//        repository.insert(word)
//    }
//}
//
//class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return WordViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
//private val _view =  MutableLiveData<View>()
//    val view : LiveData<View> = _view

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is news Fragment"
//    }
//    val text: LiveData<String> = _text
}