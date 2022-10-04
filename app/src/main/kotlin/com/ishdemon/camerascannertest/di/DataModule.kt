package com.ishdemon.camerascannertest.di

import android.content.Context
import com.ishdemon.camerascannertest.data.database.AppDatabase
import com.ishdemon.camerascannertest.data.database.dao.AlbumDao
import com.ishdemon.camerascannertest.data.database.dao.ImagesDao
import com.ishdemon.camerascannertest.data.repository.PhotosRepository
import com.ishdemon.camerascannertest.data.repository.PhotosRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindPhotosRepository(
        repositoryImpl: PhotosRepositoryImpl
    ): PhotosRepository
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase) : AlbumDao {
        return appDatabase.albumsDao()
    }

    @Provides
    fun providePhotosDao(appDatabase: AppDatabase) : ImagesDao {
        return appDatabase.imagesDao()
    }
}