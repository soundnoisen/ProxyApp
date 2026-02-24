package com.proxyapp.feature.proxy.list.ui.filters.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.Title
import com.proxyapp.feature.proxy.list.R
import com.proxyapp.feature.proxy.list.ui.list.component.HorizontalDivider
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesBottomSheet(
    selectedCountries: List<String>,
    onSelect: (String) -> Unit,
    onDelete: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val allCountryCodes = Locale.getISOCountries().sorted()
    var searchQuery by remember { mutableStateOf("") }

    val filteredCountries = allCountryCodes.filter { iso ->
        val countryName = Locale.Builder().setRegion(iso).build().displayCountry.lowercase()
        iso.lowercase().contains(searchQuery.lowercase()) || countryName.contains(searchQuery.lowercase())
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .defaultMinSize(minHeight = 100.dp),
        ) {
            Title(title = stringResource(R.string.label_countries))
            Spacer(Modifier.height(16.dp))
            SearchTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                clearVisibility = searchQuery.isNotBlank(),
                onClearClicked = { searchQuery = "" },
            )
            LazyColumn(
                modifier = Modifier.fillMaxHeight().weight(1f)
            ) {
                items(filteredCountries) { iso ->
                    Country(
                        iso = iso,
                        countryName = Locale.Builder().setRegion(iso).build().displayCountry,
                        isSelected = iso in selectedCountries,
                        onSelect = { onSelect(iso) },
                        onDelete = { onDelete(iso) }
                    )
                    if (iso != filteredCountries.last()) {
                        HorizontalDivider()
                    }
                }
                if (filteredCountries.isEmpty()) {
                    item { CountryPlaceholder() }
                }
            }
        }
    }
}