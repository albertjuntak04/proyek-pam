package com.example.proyekpam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyekpam.Episode
import com.example.proyekpam.Story

@Dao
interface EpisodeDao {
    @Insert
    fun addEpisode(episode: Episode)

//    @Query("SELECT * FROM episode WHERE idstory")
//    fun getEpisode(id:String): LiveData<List<Episode>>

}