package com.proxyapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProxyApi {
    @GET("api/proxy/search")
    suspend fun searchProxies(
        @Query("country") country: String? = null,
        @Query("protocol") protocol: String? = null,
        @Query("speed") speed: String? = null,
        @Query("page_index") pageIndex: Long,
        @Query("page_size") pageSize: Int
    ): ProxyResponse

    @POST("api/proxy/proxy_checker")
    suspend fun checkProxy(
        @Body request: ProxyCheckRequest
    ): ProxyCheckResponse
}

data class ProxyCheckRequest(
    val protocol: String,
    val servers: List<String>,
    val timeout: Int = 10
)

data class ProxyCheckResponse(
    val data: List<ProxyCheckResult>,
    val status: Int
)

data class ProxyCheckResult(
    val flag: Boolean,
    val server: String,
    val proxy: ProxyCheckDto
)

data class ProxyCheckDto(
    val ip: String,
    val port: String,
    val protocol: String,
    val speed: Double?,
    val is_valid: Boolean,
    val country: String?,
    val connect_string: String?,
    val last_checked: String?,
    val secret: String?,
    val username: String?,
    val password: String?
)

data class ProxyResponse(
    val data: ProxyData
)

data class ProxyData(
    val total_count: Int,
    val data: List<ProxyDto>
)

data class ProxyDto(
    val id: Long,
    val ip: String,
    val port: Int,
    val protocol: String,
    val speed: Double,
    val is_valid: Int,
    val country: String?,
    val connect_string: String?,
    val last_checked: String?,
    val secret: String?,
    val username: String?,
    val password: String?
)