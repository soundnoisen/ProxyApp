package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.extensions.displayName
import com.proxyapp.domain.ProxyProtocol
import com.proxyapp.feature.proxy.setup.R

@Composable
fun ProxyProtocolSegmentedSliding(
    selectedProtocol: ProxyProtocol,
    onProtocolChange: (ProxyProtocol) -> Unit,
    secret: String?,
    username: String?,
    password: String?,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecretChange: (String) -> Unit,
    secretError: String?,
    passwordError: String?,
    usernameError: String?,
) {
    val options = ProxyProtocol.entries
    val selectedIndex = options.indexOf(selectedProtocol)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        ) {
            val itemWidth = maxWidth / options.size
            val animatedOffset by animateDpAsState(
                targetValue = itemWidth * selectedIndex,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
            )
            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            )
            Row(Modifier.fillMaxSize()) {
                options.forEach { protocol ->
                    val isSelected = selectedProtocol == protocol
                    val textColor by animateColorAsState(targetValue = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onProtocolChange(protocol)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = protocol.displayName(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = selectedProtocol == ProxyProtocol.MTPROTO) {
            TextField(
                value = secret,
                onValueChange = onSecretChange,
                placeholder = stringResource(R.string.placeholder_secret),
                modifier = Modifier.fillMaxWidth(),
                errorText = secretError
            )
        }
        AnimatedVisibility(visible = selectedProtocol != ProxyProtocol.MTPROTO) {
            CredentialsForm(
                username = username,
                password = password,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                passwordError = passwordError,
                usernameError = usernameError
            )
        }
    }
}