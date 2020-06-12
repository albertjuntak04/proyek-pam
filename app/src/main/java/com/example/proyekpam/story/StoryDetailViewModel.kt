package com.example.proyekpam.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.proyekpam.Story
import com.example.proyekpam.StoryRepository
import java.util.*

class StoryDetailViewModel : ViewModel() {
    private val storyRepository = StoryRepository.get()
    private val storyIdLiveData = MutableLiveData<UUID>()

    var storyLiveData: LiveData<Story?> =
        Transformations.switchMap(storyIdLiveData){
                crimeId -> storyRepository.getStory(crimeId)
        }

    fun saveStory(story: Story){
        storyRepository.addCrime(story)
    }
}