package com.proxyapp.domain.usecase

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.SavedProxyRepository
import javax.inject.Inject

class DeleteProxyUseCase @Inject constructor(
    private val repository: SavedProxyRepository
){
    suspend operator fun invoke(proxy: Proxy) {
        repository.remove(proxy)
    }
}