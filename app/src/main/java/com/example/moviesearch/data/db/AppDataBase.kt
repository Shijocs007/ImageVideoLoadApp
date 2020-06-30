package com.example.moviesearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesearch.data.model.ImageData

@Database(entities = [ImageData::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getMyDao() : MyDao

    companion object {

        @Volatile
        private var instance : AppDataBase ? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

                instance?:buildDatabase(context).also {
                    instance = it
                }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "ImageVideoLoader.db"
        ).build()
    }

}