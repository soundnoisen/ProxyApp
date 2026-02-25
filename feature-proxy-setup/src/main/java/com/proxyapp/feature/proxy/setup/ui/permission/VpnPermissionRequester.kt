package com.proxyapp.feature.proxy.setup.ui.permission

import android.app.Activity
import android.net.VpnService
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun VpnPermissionRequester(onPermissionGranted: () -> Unit, onPermissionDenied: (() -> Unit)? = null): () -> Unit {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onPermissionGranted()
        } else {
            onPermissionDenied?.invoke()
            Toast.makeText(context, "VPN permission denied", Toast.LENGTH_LONG).show()
        }
    }

    return {
        val intent = VpnService.prepare(context)
        if (intent != null) {
            launcher.launch(intent)
        } else {
            onPermissionGranted()
        }
    }
}