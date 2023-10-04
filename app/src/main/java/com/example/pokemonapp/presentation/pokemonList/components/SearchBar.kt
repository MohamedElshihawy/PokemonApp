package com.example.pokemonapp.presentation.pokemonList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String,
    text: String,
    isHintDisplayed: Boolean,
    onSearch: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                onSearch(it)
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = CircleShape)
                .background(Color.White, shape = CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    onFocusChanged(it)
                },
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp),
            )
        }
    }
}
