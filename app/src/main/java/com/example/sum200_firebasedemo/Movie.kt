package com.example.sum200_firebasedemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    /**
     * Id used as primary key. The id is auto generated.
     */
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    /**
     * Column for movie title
     */
    @ColumnInfo(name = "title") val title: String,
)
