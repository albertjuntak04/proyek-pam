package com.example.proyekpam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyekpam.Episode
import com.example.proyekpam.Story
import java.util.*

@Dao
interface EpisodeDao {
    @Update
    fun updateEpisode(episode: Episode)

    @Insert
    fun addEpisode(episode: Episode)

    @Query("SELECT * FROM episode WHERE idStory=(:id)")
    fun getEpisode(id:UUID): LiveData<List<Episode>>

    @Query("SELECT * FROM episode WHERE id=(:id)")
    fun getDetailEpisode(id:UUID): LiveData<Episode?>



}