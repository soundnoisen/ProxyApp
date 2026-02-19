package com.proxyapp.feature.proxy.setup.data.di

import com.proxyapp.feature.proxy.setup.data.repository.ProxySetupRepositoryImpl
import com.proxyapp.feature.proxy.setup.domain.repository.ProxySetupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProxySetupModule {
    @Binds
    abstract fun bindProxySetupRepository(impl: ProxySetupRepositoryImpl): ProxySetupRepository
}