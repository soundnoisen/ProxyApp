package com.proxyapp.feature.proxy.list.ui.filters.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.feature.proxy.list.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryFilter(
    selectedCountries: List<String>,
    openSheet: () -> Unit,
    onDelete: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LabelFilter(text = stringResource(R.string.label_countries),)
        Box(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = MaterialTheme.colorScheme.onSurface)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedCountries.isNotEmpty()) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        selectedCountries.chunked(3).forEach { rowCountries ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowCountries.forEach { iso ->
                                    CountryTag(
                                        iso = iso,
                                        onDelete = { onDelete(iso) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                repeat(3 - rowCountries.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.hint_countries),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = openSheet) {
                    Icon(
                        painter = painterResource(com.proxyapp.core.ui.R.drawable.ic_add),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}