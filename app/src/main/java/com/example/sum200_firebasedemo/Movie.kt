package com.example.sum200_firebasedemo

/**
 * Data class for representing a movie.
 *
 * Note, Firestore is schemaless so fields may or may not exist.
 * Our data class must therefore provide default values.
 */
data class Movie(
    val title: String = "",
)
