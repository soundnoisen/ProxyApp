package com.proxyapp.data.di

import com.proxyapp.data.repository.ProxyRepositoryImpl
import com.proxyapp.data.repository.SavedProxyRepositoryImpl
import com.proxyapp.domain.repository.ProxyRepository
import com.proxyapp.domain.repository.SavedProxyRepository
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
}