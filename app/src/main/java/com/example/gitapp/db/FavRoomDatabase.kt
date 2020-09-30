package com.example.gitapp.adapter

import android.content.Context
import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitapp.model.Favorite

//Database annotation to specify the entities and set version
@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavRoomDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: FavRoomDatabase? = null

        fun getDatabase(context: Context): FavRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavRoomDatabase::class.java,
                    "fav_db"
                )
                    .allowMainThreadQueries() //allows Room to executing task in main thread
                    .fallbackToDestructiveMigration() //allows Room to recreate table if no migrations found
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getNoteDao() : FavDao

}
