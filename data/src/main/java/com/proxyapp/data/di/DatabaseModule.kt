package com.proxyapp.data.di

import android.content.Context
import androidx.room.Room
import com.proxyapp.data.local.AppDatabase
import com.proxyapp.data.local.ProxyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "proxy_app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProxyDao(db: AppDatabase): ProxyDao = db.proxyDao()
}