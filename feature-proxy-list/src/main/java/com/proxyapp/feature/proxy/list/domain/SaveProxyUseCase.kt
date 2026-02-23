package com.proxyapp.feature.proxy.list.domain

import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.SaveResult
import com.proxyapp.domain.repository.ProxyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProxyUseCase @Inject constructor(
    private val repository: ProxyRepository
){
    suspend operator fun invoke(proxy: Proxy): Flow<SaveResult> {
        return repository.save(proxy)
    }
}