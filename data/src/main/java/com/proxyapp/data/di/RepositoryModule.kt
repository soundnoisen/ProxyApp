package com.proxyapp.data.di

import com.proxyapp.data.repository.ProxyRepositoryImpl
import com.proxyapp.data.repository.SavedProxyRepositoryImpl
import com.proxyapp.data.repository.ThemeRepositoryImpl
import com.proxyapp.domain.repository.ProxyRepository
import com.proxyapp.domain.repository.SavedProxyRepository
import com.proxyapp.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProxyRepository(impl: ProxyRepositoryImpl): ProxyRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesProxyRepository(impl: SavedProxyRepositoryImpl): SavedProxyRepository

    @Binds
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository
}