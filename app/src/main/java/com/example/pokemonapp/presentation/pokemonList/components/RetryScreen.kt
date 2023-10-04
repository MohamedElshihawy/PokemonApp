package com.example.pokemonapp.presentation.pokemonList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun RetryLoading(
    errorMessage: String,
    onRetry: () -> Unit,
) {
    Column {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 24.sp,
        )

        Button(
            onClick = {
                onRetry()
            },
            modifier = Modifier.align(CenterHorizontally),
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reload",
            )
        }
    }
}
