package com.example.pokemonapp.presentation.pokemonInfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.data.remote.response.Type
import com.example.pokemonapp.utills.parseTypeToColor

@Composable
fun TypesSection(
    types: List<Type>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .height(36.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type)),
            ) {
                Text(
                    text = type.type.name,
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
    }
}
