package com.example.workout.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SchedulerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is training scheduler Fragment"
    }
    val text: LiveData<String> = _text
}