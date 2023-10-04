package com.example.pokemonapp.presentation.pokemonInfo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.data.remote.response.Pokemon
import java.util.Locale

@Composable
fun PokemonDetailsSection(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(
                scrollState,
            )
            .padding(8.dp),
    ) {
        Text(
            text = pokemon.name.replaceFirstChar
                { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        TypesSection(types = pokemon.types)

        PokemonDataSection(
            pokemonWeight = pokemon.weight,
            pokemonHeight = pokemon.height,
        )

        StatesSection(pokemon = pokemon)
    }
}
