package com.proxyapp.data.mapper

import com.proxyapp.data.local.ProxyEntity
import com.proxyapp.data.remote.ProxyDto
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.ProxySource

fun ProxyDto.toEntity() = ProxyEntity(
    id = id.toString(),
    ip = ip,
    port = port,
    speed = speed.toFloat(),
    protocol = protocol,
    isValid = is_valid == 1,
    country = country,
    connectString = connect_string,
    lastChecked = last_checked,
    secret = secret,
    username = username,
    password = password
)

fun ProxyEntity.toDomain() = Proxy(
    id = id,
    ip = ip,
    port = port,
    speed = speed,
    protocol = ProxyProtocol.fromString(protocol),
    isValid = isValid,
    country = country,
    connectString = connectString,
    lastChecked = lastChecked,
    secret = secret,
    username = username,
    password = password,
    source = if (isManual) ProxySource.MANUAL else ProxySource.API
)

fun ProxyDto.toDomain(): Proxy {
    return Proxy(
        id = id.toString(),
        ip = ip,
        port = port,
        speed = speed.toFloat(),
        protocol = ProxyProtocol.fromString(protocol),
        isValid = is_valid == 1,
        country = country,
        connectString = connect_string,
        lastChecked = last_checked,
        secret = secret,
        username = username,
        password = password,
        source = ProxySource.API
    )
}


fun Proxy.toEntity(): ProxyEntity {
    return ProxyEntity(
        id = id,
        ip = ip,
        port = port,
        speed = speed,
        protocol = protocol.name.lowercase(),
        isValid = isValid,
        country = country,
        connectString = connectString,
        lastChecked = lastChecked,
        secret = secret,
        username = username,
        password = password,
        isManual = source == ProxySource.MANUAL
    )
}