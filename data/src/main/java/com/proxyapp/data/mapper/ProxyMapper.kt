package com.proxyapp.data.mapper

import com.proxyapp.data.remote.ProxyDto
import com.proxyapp.domain.model.Proxy
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.domain.model.ProxySource

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