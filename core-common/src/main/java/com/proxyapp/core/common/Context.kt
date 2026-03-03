package com.proxyapp.core.common

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.openUrl(url: String): Boolean {
    return try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                url.toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
        true
    } catch (e: Exception) {
        false
    }
}