package com.example.proyekpam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Episode(@PrimaryKey val id: UUID = UUID.randomUUID(),
                   var titleEpisode: String = "",
                   var date: String = " ",
                   var fieldStory: String = " ",
                   var idStory: UUID = UUID.randomUUID()
   ) {
}