package com.example.proyekpam.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyekpam.Episode
import com.example.proyekpam.StoryRepository
import java.util.*

class EpisodeDetailViewModel: ViewModel() {
    private val storyRepository = StoryRepository.get()
    private val storyIdLiveData = MutableLiveData<String>()

    fun saveEpisode(episode: Episode){
        storyRepository.addEpisode(episode)
    }

    fun loadEpisode(crimeId: String){
        storyIdLiveData.value = crimeId
    }
}