package com.example.workout.di


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.workout.database.ExerciseDatabase
import com.example.workout.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.workout.other.Constants.KEY_NAME
import com.example.workout.other.Constants.KEY_WEIGHT
import com.example.workout.other.Constants.RUNNING_DATABASE_NAME
import com.example.workout.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
            @ApplicationContext app: Context
    ) = Room.databaseBuilder(
            app,
            ExerciseDatabase::class.java,
            RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: ExerciseDatabase) = db.getExerciseDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
            app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
            sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}