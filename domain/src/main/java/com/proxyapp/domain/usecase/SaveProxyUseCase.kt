package com.proxyapp.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.SaveError
import com.proxyapp.domain.model.SaveResult
import com.proxyapp.domain.repository.SavedProxyRepository
import javax.inject.Inject

class SaveProxyUseCase @Inject constructor(
    private val repository: SavedProxyRepository
){
    suspend operator fun invoke(proxy: Proxy): SaveResult {
        val exists = repository.exists(proxy)
        return if (!exists) {
            repository.add(proxy)
            SaveResult.Success
        } else {
            SaveResult.Error(SaveError.EXISTS)
        }
    }
}