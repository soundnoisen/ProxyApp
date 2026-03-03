package com.proxyapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProxyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(proxy: ProxyEntity)

    @Query("DELETE FROM saved_proxies")
    suspend fun clearAll()

    @Query("SELECT * FROM saved_proxies")
    suspend fun getAll(): List<ProxyEntity>

    @Update
    suspend fun update(proxy: ProxyEntity)

    @Update
    suspend fun update(proxies: List<ProxyEntity>)

    @Query("SELECT * FROM saved_proxies ORDER BY id ASC")
    fun observeAll(): Flow<List<ProxyEntity>>

    @Query("SELECT * FROM saved_proxies WHERE id = :id")
    suspend fun getById(id: String): ProxyEntity?

    @Delete
    suspend fun delete(proxy: ProxyEntity)

    @Query("SELECT * FROM saved_proxies WHERE ip = :ip AND port = :port LIMIT 1")
    suspend fun getByIpPort(ip: String, port: Int): ProxyEntity?
}