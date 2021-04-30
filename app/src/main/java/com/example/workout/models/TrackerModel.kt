package com.example.workout.models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.workout.database.Exercise
import com.example.workout.repo.MainRepo
import kotlinx.coroutines.launch
import java.util.*

class TrackerModel @ViewModelInject constructor(
    val mainRepository: MainRepo
): ViewModel() {

    var date = Calendar.getInstance().timeInMillis

    private val exerciseByDate = mainRepository.getExerciseByDate(date)

    val exercises = MediatorLiveData<List<Exercise>>()

    init {
        exercises.addSource(exerciseByDate) { result ->
            result?.let { exercises.value = it }
        }
    }

    fun insertExercise(exercise: Exercise) = viewModelScope.launch {
        mainRepository.insertExercise(exercise)
    }

    fun nukeTable() = viewModelScope.launch {
        mainRepository.nukeTable()
    }
}