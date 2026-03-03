package com.proxyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_proxies")
data class ProxyEntity(
    @PrimaryKey val id: String,
    val ip: String,
    val port: Int,
    val protocol: String,
    val speed: Float = 0f,
    val isValid: Boolean = false,
    val country: String? = null,
    val connectString: String? = null,
    val lastChecked: String? = null,
    val secret: String? = null,
    val username: String? = null,
    val password: String? = null,
    val isManual: Boolean = false
)