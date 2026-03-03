package com.proxyapp.data.repository

import android.util.Log
import com.proxyapp.data.mapper.toDomain
import com.proxyapp.data.remote.ProxyApi
import com.proxyapp.data.remote.ProxyCheckDto
import com.proxyapp.data.remote.ProxyCheckRequest
import com.proxyapp.domain.extensions.toServerString
import com.proxyapp.domain.model.LoadError
import com.proxyapp.domain.model.LoadProgress
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyFilters
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.repository.ProxyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ProxyRepositoryImpl @Inject constructor(
    private val api: ProxyApi,
) : ProxyRepository {

    private val _proxies = MutableStateFlow<List<Proxy>>(emptyList())
    override fun observeProxies(): Flow<List<Proxy>> = _proxies

    private val pageCache = mutableMapOf<ProxyFilters, MutableList<Proxy>>()
    private val currentPages = mutableMapOf<ProxyFilters, Int>()
    private val endOfPages = mutableMapOf<ProxyFilters, Boolean>()

    override fun refresh(filters: ProxyFilters): Flow<LoadProgress> = flow {
        emit(LoadProgress.Loading)
        try {
            pageCache[filters] = mutableListOf()
            val (proxies, end) = loadPageCached(filters, 1)
            _proxies.value = proxies
            currentPages[filters] = 1
            endOfPages[filters] = end
            emit(LoadProgress.Success)
        } catch (e: Exception) {
            Log.e("ProxyRepository", "refresh error", e)
            emit(LoadProgress.Error(e.toLoadError()))
        }
    }

    override fun loadNextPage(filters: ProxyFilters): Flow<LoadProgress> = flow {
        if (endOfPages[filters] == true) return@flow emit(LoadProgress.Success)
        emit(LoadProgress.Loading)
        try {
            val nextPage = (currentPages[filters] ?: 1) + 1
            val (newProxies, end) = loadPageCached(filters, nextPage)
            _proxies.value = _proxies.value + newProxies
            currentPages[filters] = nextPage
            endOfPages[filters] = end
            emit(LoadProgress.Success)
        } catch (e: Exception) {
            Log.e("ProxyRepository", "loadNextPage error", e)
            emit(LoadProgress.Error(e.toLoadError()))
        }
    }

    private suspend fun loadPageCached(filters: ProxyFilters, page: Int): Pair<List<Proxy>, Boolean> {
        val cached = pageCache.getOrPut(filters) { mutableListOf() }
        val pageSize = 30

        val start = (page - 1) * pageSize
        if (start < cached.size) {
            val end = minOf(page * pageSize, cached.size)
            val endOfPages = cached.size < page * pageSize
            return cached.subList(start, end) to endOfPages
        }

        val (newProxies, end) = loadPage(filters, page)
        cached.addAll(newProxies)
        return newProxies to end
    }

    private suspend fun loadPage(filters: ProxyFilters, page: Int): Pair<List<Proxy>, Boolean> {
        val pageSize = 30
        val protocols = if (filters.telegramFilter) {
            "${ProxyProtocol.MTPROTO.name.lowercase()},${ProxyProtocol.SOCKS5.name.lowercase()}"
        } else {
            filters.protocols.joinToString(",") { it.name.lowercase() }.takeIf { it.isNotEmpty() }
        }

        val response = api.searchProxies(
            country = filters.countriesIso.joinToString(",").takeIf { it.isNotEmpty() },
            protocol = protocols,
            speed = "${filters.speedRange.minSpeed},${filters.speedRange.maxSpeed}",
            pageIndex = page.toLong(),
            pageSize = pageSize
        )

        val proxies = response.data.data
            .filter { !filters.activeOnly || it.is_valid == 1 }
            .map { it.toDomain() }

        val endOfPages = (page * pageSize) >= response.data.total_count
        return proxies to endOfPages
    }

    override suspend fun checkProxy(proxy: Proxy): Proxy {
        val resultMap = checkServers(proxy.protocol, listOf(proxy))
        val result = resultMap[proxy.toServerString()] ?: return proxy
        return proxy.copy(
            speed = result.speed?.toFloat() ?: 0f,
            lastChecked = result.last_checked
        )
    }

    override suspend fun checkProxies(protocol: ProxyProtocol, proxies: List<Proxy>): List<Proxy> {
        val resultMap = checkServers(protocol, proxies)
        return proxies.map { proxy ->
            val result = resultMap[proxy.toServerString()]
            if (result != null) {
                proxy.copy(
                    speed = result.speed?.toFloat() ?: 0f,
                    lastChecked = result.last_checked
                )
            } else {
                proxy
            }
        }
    }

    private suspend fun checkServers(protocol: ProxyProtocol, proxies: List<Proxy>
    ): Map<String, ProxyCheckDto> = withContext(Dispatchers.IO) {
        try {
            val response = api.checkProxy(
                ProxyCheckRequest(
                    protocol = protocol.name.lowercase(),
                    servers = proxies.map { it.toServerString() }
                )
            )
            response.data.associate { dto -> dto.server to dto.proxy }
        } catch (e: IOException) {
            Log.e("CheckProxies", "Network error: ${e.message}", e)
            emptyMap()
        } catch (e: Exception) {
            Log.e("CheckProxies", "Unknown error: ${e.message}", e)
            emptyMap()
        }
    }

    private fun Throwable.toLoadError(): LoadError = when (this) {
        is IOException -> LoadError.NETWORK
        else -> LoadError.UNKNOWN
    }
}