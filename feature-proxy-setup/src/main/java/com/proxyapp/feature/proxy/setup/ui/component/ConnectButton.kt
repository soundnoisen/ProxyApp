package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.proxyapp.feature.proxy.setup.R
import com.proxyapp.feature.proxy.setup.domain.model.ConnectionStatus
import kotlinx.coroutines.delay

@Composable
fun ConnectButton(
    status: ConnectionStatus,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val isConnecting = status == ConnectionStatus.CONNECTING

    var pressed by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.98f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Reverse
        )
    )

    val buttonSize by animateDpAsState(
        targetValue = if (pressed) 176.dp else 180.dp,
        animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
    )
    val iconSize by animateDpAsState(
        targetValue = if (pressed) 56.dp else 60.dp,
        animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
    )
    val elevation by animateDpAsState(
        targetValue = if (pressed) 2.dp else 4.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .size(180.dp)
            .graphicsLayer {
                scaleX = if (isConnecting) pulseScale else 1f
                scaleY = if (isConnecting) pulseScale else 1f
            },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            enabled = enabled,
            onClick = { pressed = true },
            border = BorderStroke(20.dp, MaterialTheme.colorScheme.outlineVariant),
            shape = CircleShape,
            shadowElevation = elevation,
            modifier = Modifier.size(buttonSize),
            color = if (status == ConnectionStatus.CONNECTED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isConnecting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(56.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 4.dp
                    )
                }
                else {
                    Icon(
                        painter = painterResource(R.drawable.ic_connect),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
            onClick()
        }
    }
}