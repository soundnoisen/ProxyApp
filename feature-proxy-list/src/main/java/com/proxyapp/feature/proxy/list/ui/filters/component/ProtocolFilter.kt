package com.proxyapp.feature.proxy.list.ui.filters.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.domain.model.ProxyProtocol
import com.proxyapp.feature.proxy.list.R

@Composable
fun ProtocolFilter(
    protocolsSelected: List<ProxyProtocol>?,
    onClick: (ProxyProtocol) -> Unit
) {
    val protocols = ProxyProtocol.entries.toTypedArray()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LabelFilter(text = stringResource(R.string.label_protocols))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            protocols.forEach { protocol ->
                val isSelected = protocolsSelected?.contains(protocol) == true
                val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

                Box(
                    modifier = Modifier
                        .shadow(8.dp, CircleShape, spotColor = MaterialTheme.colorScheme.surfaceTint)
                        .background(backgroundColor, CircleShape)
                        .clickable{ onClick(protocol) }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = protocol.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
            }
        }
    }
}