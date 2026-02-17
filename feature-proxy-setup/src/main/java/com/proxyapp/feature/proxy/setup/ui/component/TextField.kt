package com.proxyapp.feature.proxy.setup.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.proxyapp.core.ui.component.ErrorText

@Composable
fun TextField(
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    errorText: String?,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    isNumber: Boolean = false,
) {
    val isError = errorText != null
    val keyboardType = when {
        isPassword -> KeyboardType.Password
        isNumber -> KeyboardType.Number
        else -> KeyboardType.Text
    }
    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value.orEmpty(),
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = enabled,
            singleLine = true,
            isError = isError,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = visualTransformation
        )
        ErrorText(
            text = errorText.orEmpty(),
            visibility = isError,
        )
    }
}