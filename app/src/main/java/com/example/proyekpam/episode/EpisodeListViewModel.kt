package com.example.proyekpam.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.proyekpam.Episode
import com.example.proyekpam.Story
import com.example.proyekpam.StoryRepository
import java.util.*

class EpisodeListViewModel: ViewModel() {
      val episodeRepository = StoryRepository.get()
      private val episodeIdLiveData = MutableLiveData<UUID>()

    var episodeLiveData: LiveData<List<Episode>> =
            Transformations.switchMap(episodeIdLiveData){
                        storyId -> episodeRepository.getEpisode(storyId)
            }



      fun loadEpisode(episodeId: UUID){
            episodeIdLiveData.value = episodeId
      }

    fun addEpisode(episode: Episode){
        episodeRepository.addEpisode(episode)
    }



}