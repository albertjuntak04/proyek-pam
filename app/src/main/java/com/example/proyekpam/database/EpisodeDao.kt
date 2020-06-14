package com.example.proyekpam.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyekpam.Episode
import com.example.proyekpam.Story
import java.util.*

@Dao
interface EpisodeDao {
    @Update
    fun updateEpisode(episode: Episode)

    @Insert
    fun addEpisode(episode: Episode)

    @Delete
    fun deleteEpisode(episode: Episode)

    @Query("SELECT * FROM episode WHERE idStory=(:id)")
    fun getEpisode(id:UUID): LiveData<List<Episode>>

    @Query("SELECT * FROM episode WHERE id=(:id)")
    fun getDetailEpisode(id:UUID): LiveData<Episode?>

}