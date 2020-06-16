package com.example.proyekpam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Story(@PrimaryKey val id:UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: String = " "
) {
}
