package com.example.proyekpam.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyekpam.Episode
import com.example.proyekpam.Story

@Database(entities = [Story::class,Episode::class], version = 1)
@TypeConverters(StoryTypeConverters::class)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun episodeDao(): EpisodeDao
}

val migration_1_2 = object : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Story ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}