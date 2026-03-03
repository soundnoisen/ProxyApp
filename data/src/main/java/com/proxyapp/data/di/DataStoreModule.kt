package com.proxyapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.proxyapp.data.repository.FiltersRepositoryImpl
import com.proxyapp.domain.repository.FiltersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    @FiltersDataStore
    fun provideFiltersDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("filters_pref") }
        )
    }

    @Provides
    @Singleton
    fun provideFiltersRepository(@FiltersDataStore dataStore: DataStore<Preferences>): FiltersRepository {
        return FiltersRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    @ProxyDataStore
    fun provideProxyDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("connected_proxy_id") }
        )
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FiltersDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProxyDataStore