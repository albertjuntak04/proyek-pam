package com.example.proyekpam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyekpam.Story
import java.util.*

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getStories(): LiveData<List<Story>>

    @Query("SELECT * FROM story WHERE id=(:id)")
    fun getStory(id: UUID): LiveData<Story?>

    @Insert
    fun addStory(story: Story)
}