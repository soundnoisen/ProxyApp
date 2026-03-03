package com.proxyapp.data.repository

import com.proxyapp.data.local.ProxyDao
import com.proxyapp.data.mapper.toDomain
import com.proxyapp.data.mapper.toEntity
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.repository.SavedProxyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedProxyRepositoryImpl @Inject constructor(
    private val dao: ProxyDao
): SavedProxyRepository {

    override suspend fun add(proxy: Proxy) {
        dao.insert(proxy.toEntity())
    }

    override suspend fun remove(proxy: Proxy) {
        dao.delete(proxy.toEntity())
    }

    override fun observeSavedProxies(): Flow<List<Proxy>> {
        return dao.observeAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun update(proxy: Proxy) {
        dao.update(proxy.toEntity())
    }

    override suspend fun update(proxies: List<Proxy>) {
        dao.update(proxies.map { it.toEntity() })
    }

    override suspend fun exists(proxy: Proxy): Boolean {
        return dao.getByIpPort(proxy.ip, proxy.port) != null
    }
}