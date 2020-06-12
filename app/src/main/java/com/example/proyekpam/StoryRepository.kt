package com.example.proyekpam

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.proyekpam.database.EpisodeDao
import com.example.proyekpam.database.StoryDatabase
import com.example.proyekpam.database.migration_1_2
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "proyek-date"

class StoryRepository private constructor(context: Context){
    private val database : StoryDatabase = Room.databaseBuilder(
        context.applicationContext,
        StoryDatabase::class.java,
        DATABASE_NAME
    ).build()
//    ).addMigrations(migration_1_2).build()

    private val storyDao = database.storyDao()
    private val episodeDao = database.episodeDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val fileDir = context.applicationContext.filesDir

    fun getStories(): LiveData<List<Story>> = storyDao.getStories()
//    fun getEpisode(): LiveData<List<Episode>> = episodeDao.getEpisode()
    fun getStory(id: UUID): LiveData<Story?> = storyDao.getStory(id)

    fun addCrime(story: Story){
        executor.execute {
            storyDao.addStory(story)
        }
    }

//    fun getEpisode(id: String){
//        executor.execute {
//            episodeDao.getEpisode()
//        }
//    }

//    fun getEpisode(id: String): LiveData<List<Episode>> = episodeDao.getEpisode(id)

    fun addEpisode(episode: Episode){
        executor.execute {
            episodeDao.addEpisode(episode)
        }
    }

    companion object{
        private var INSTANCE : StoryRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = StoryRepository(context)
            }
        }

        fun get(): StoryRepository{
            return INSTANCE?:
            throw IllegalStateException("StoryRepository must be initialized")
        }
    }
}