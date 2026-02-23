package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.repository.FiltersRepository
import javax.inject.Inject

class SaveFiltersUseCase @Inject constructor(
    private val repository: FiltersRepository
) {
    suspend operator fun invoke(filters: ProxyFilters) {
        repository.saveFilters(filters)
    }
}