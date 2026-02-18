package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.proxyapp.feature.proxy.setup.R

@Composable
fun CredentialsForm(
    username: String?,
    password: String?,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    usernameError: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.label_credentials),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                placeholder = stringResource(R.string.placeholder_username),
                modifier = Modifier.weight(1f),
                errorText = usernameError
            )
            TextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = stringResource(R.string.placeholder_password),
                isPassword = true,
                modifier = Modifier.weight(1f),
                errorText = passwordError
            )
        }
    }
}