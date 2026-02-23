package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.repository.FiltersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFiltersUseCase @Inject constructor(
    private val repository: FiltersRepository
) {
    operator fun invoke(): Flow<ProxyFilters> {
        return repository.observeFilters()
    }
}