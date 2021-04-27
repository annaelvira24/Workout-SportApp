package com.example.workout.ui.history

import androidx.lifecycle.*
import com.example.workout.database.History
import com.example.workout.database.HistoryRepository
import com.example.workout.retrofitclient.News
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLogs: LiveData<List<History>> = repository.historyLogs.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(history: History) = viewModelScope.launch {
        repository.insert(history)
    }
}

class HistoryViewModelFactory(private val repository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class HistoryViewModel : ViewModel() {

//    private var newsListView: MutableLiveData<ArrayList<News>>? = null

//    internal fun getNewsList(): MutableLiveData<ArrayList<News>> {
//        if (fruitList == null) {
//            fruitList = MutableLiveData()
//            loadFruits()
//        }
//        return fruitList as MutableLiveData<List<Model>>
//    }
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is training history Fragment"
//    }
//    val text: LiveData<String> = _text
//}