package com.example.proyekpam.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.proyekpam.Episode
import com.example.proyekpam.Story
import com.example.proyekpam.StoryRepository

class EpisodeListViewModel: ViewModel() {
      val episodeRepository = StoryRepository.get()
      private val episodeIdLiveData = MutableLiveData<String>()

//    var episodeLiveData: LiveData<List<Episode>> =
//        Transformations.switchMap(episodeIdLiveData){
//                storyId -> episodeRepository.getEpisode(storyId)
//        }



//    fun loadEpisode(crimeId: String){
//        episodeIdLiveData.value = crimeId
//    }
}