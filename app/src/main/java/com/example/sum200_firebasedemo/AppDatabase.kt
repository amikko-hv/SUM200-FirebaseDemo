package com.example.sum200_firebasedemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for the application
 */
@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Movie data access object
     */
    abstract fun movieDao(): MovieDao

    /**
     * Singleton companion object to reuse a database instance
     */
    companion object Singleton {
        private var instance: AppDatabase? = null

        /**
         * Get a database instance.
         * A database instance will be created if it doesn't exist.
         *
         * @param context Database context. Typically uses the application context.
         */
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app-database",
                ).build()
            }

            return instance as AppDatabase
        }
    }
}