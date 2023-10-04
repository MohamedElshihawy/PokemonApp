package com.example.pokemonapp.presentation.pokemonInfo.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.utills.Resource

@Composable
fun PokemonInfoStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingProgressBarModifier: Modifier,
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailsSection(
                pokemon = pokemonInfo.data!!,
                modifier = modifier,
            )
        }

        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier,
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingProgressBarModifier,
            )
        }
    }
}
