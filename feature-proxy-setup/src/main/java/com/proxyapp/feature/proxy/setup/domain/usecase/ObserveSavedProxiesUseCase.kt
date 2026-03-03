package com.proxyapp.feature.proxy.setup.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.SavedProxyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSavedProxiesUseCase @Inject constructor(
    private val repository: SavedProxyRepository
){
    operator fun invoke(): Flow<List<Proxy>> {
        return repository.observeSavedProxies()
    }
}