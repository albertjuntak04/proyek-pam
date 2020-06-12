package com.example.proyekpam.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.proyekpam.Episode
import com.example.proyekpam.StoryRepository
import java.util.*

class EpisodeDetailViewModel: ViewModel() {
    private val episodeRepository = StoryRepository.get()
    private val episodeIdLiveData = MutableLiveData<UUID>()


    fun saveEpisode(episode: Episode){
        episodeRepository.updateEpisode(episode)
    }

    var episodeLiveData: LiveData<Episode?> =
        Transformations.switchMap(episodeIdLiveData){
                episodeId -> episodeRepository.getDetailEpisode(episodeId)
        }

    fun loadEpisode(episodeId: UUID){
        episodeIdLiveData.value = episodeId
    }

    fun addEpisode(episode: Episode){
        episodeRepository.addEpisode(episode)
    }
}