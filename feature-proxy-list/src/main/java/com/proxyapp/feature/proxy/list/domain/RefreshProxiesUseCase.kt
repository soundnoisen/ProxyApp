package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.LoadProgress
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.repository.ProxyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshProxiesUseCase @Inject constructor(
    private val repository: ProxyRepository
) {
    operator fun invoke(filters: ProxyFilters): Flow<LoadProgress> {
        return repository.refresh(filters)
    }
}