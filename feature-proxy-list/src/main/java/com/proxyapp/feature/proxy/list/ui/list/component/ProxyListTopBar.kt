package com.proxyapp.feature.proxy.list.ui.list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.Title
import com.proxyapp.feature.proxy.list.R

@Composable
fun ProxyListTopBar(
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Title(title = stringResource(R.string.title_proxy_list))
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .shadow(4.dp, shape = CircleShape, spotColor = MaterialTheme.colorScheme.onSurface, ambientColor = MaterialTheme.colorScheme.onSurface)
            ,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filters),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}