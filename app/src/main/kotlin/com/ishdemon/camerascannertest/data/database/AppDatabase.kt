package com.ishdemon.camerascannertest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ishdemon.camerascannertest.data.database.dao.AlbumDao
import com.ishdemon.camerascannertest.data.database.dao.ImagesDao
import com.ishdemon.camerascannertest.data.entity.AlbumEntity
import com.ishdemon.camerascannertest.data.entity.ImageEntity

@Database(entities = [AlbumEntity::class, ImageEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumDao
    abstract fun imagesDao(): ImagesDao

    companion object {
        private const val DATABASE_NAME = "cameraScannerDb"
        fun buildDatabase(context : Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}