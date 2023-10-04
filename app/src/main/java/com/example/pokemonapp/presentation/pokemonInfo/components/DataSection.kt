package com.example.pokemonapp.presentation.pokemonInfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R

@Composable
fun PokemonDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Int = 100,
) {
    val weightKG = remember {
        pokemonWeight / 10f
    }
    val heightM = remember {
        pokemonHeight / 10f
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        PokemonDataItem(
            dataValue = weightKG,
            measureUnit = "Kg",
            icon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f),
        )

        Spacer(
            modifier = Modifier
                .size(
                    width = 1.dp,
                    height = sectionHeight.dp,
                )
                .background(Color.Gray),
        )
        PokemonDataItem(
            dataValue = heightM,
            measureUnit = "M",
            icon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun PokemonDataItem(
    dataValue: Float,
    measureUnit: String,
    icon: Painter,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(painter = icon, contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue$measureUnit",
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
