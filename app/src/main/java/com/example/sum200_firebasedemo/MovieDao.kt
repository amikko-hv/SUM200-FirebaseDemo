package com.example.sum200_firebasedemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    /**
     * Get all movies from the database
     */
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    /**
     * Insert movies into the database.
     */
    @Insert
    suspend fun insertAll(vararg movies: Movie)
}