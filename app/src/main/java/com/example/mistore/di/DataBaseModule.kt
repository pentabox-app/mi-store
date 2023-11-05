package com.example.mistore.di

import android.content.Context
import androidx.room.Room
import com.example.mistore.db.ProductDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideProductDb(@ApplicationContext context:Context):ProductDb{
        return Room.databaseBuilder(context, ProductDb::class.java, "ProductDB").build()

    }


}