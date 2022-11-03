package com.example.jetpackcompose.view_model

import android.app.Application
import android.util.Log

import androidx.compose.runtime.*
import androidx.lifecycle.*

import com.example.jetpackcompose.RewardRepository
import com.example.jetpackcompose.RoomDataBase
import com.example.jetpackcompose.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardListViewModel(application: Application):AndroidViewModel(application) {

    val repository:RewardRepository
    private var reallAll:LiveData<List<RewardUser>>
    private lateinit var moviesListLiveData:MutableLiveData<List<Movies>>
    init {
        val rewardDao = RoomDataBase.getDatabase(application).rewardDao()
        val movieDao = RoomDataBase.getDatabase(application).moviesDao()
        repository = RewardRepository(rewardDao,movieDao)
        addReward()
        moviesListLiveData =MutableLiveData()
        reallAll =repository.getRewardFromRoom()

    }

    fun getRewardObservable():  LiveData<List<RewardUser>>{
        return reallAll
    }
    fun getAllMoviesObservable():  LiveData<List<Movies>>{
        return moviesListLiveData
    }
    private fun addReward(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var hobbies= listOf<String>("Reading books")
                var rewadCategory =RewardCategoryServices(
                    "HvFng9fn9pQNx15NPqcNhbNwYpr1",
                    "vfx editor","decription",100,
                    "xyz"

                )
                var categoryServicesList = listOf<RewardCategoryServices>(rewadCategory)
                repository.addRewardToRoom(RewardUser(
                    "8WfpWdyBSSZ6BDUY4Tu4rZ0pNcm1",
                    "Hemanth sai",
                    hobbies,
                    "",
                   categoryServicesList
                ))
                repository.addRewardToRoom(RewardUser(
                    "oGZBB1nwNKMCcCUF8ydTa34YXvK2",
                    "sai",
                    hobbies,
                    "",
                    categoryServicesList
                ))



            }
            catch (err:Throwable){

            }
        }
    }



    fun getMovieResults() {
       // viewModelScope.launch {
           repository.getAllMovies(moviesListLiveData,repository,viewModelScope)
           // moviesListLiveData.value =data.result
       // }

    }
}