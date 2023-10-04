package com.example.pokemonapp.presentation.pokemonInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.presentation.pokemonInfo.components.PokemonInfoStateWrapper
import com.example.pokemonapp.presentation.pokemonInfo.components.TopBar
import com.example.pokemonapp.utills.Resource

@Composable
fun PokemonInfoScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Int = 20,
    pokemonImageSize: Int = 200,
    viewModel: PokemonInfoViewModel = hiltViewModel(),
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor),
    ) {
        TopBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2f),
        )

        PokemonInfoStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = (topPadding + (pokemonImageSize / 2)).dp,
                )
                .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    16.dp,
                )
                .align(Alignment.BottomCenter),
            loadingProgressBarModifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = (topPadding + (pokemonImageSize / 2)).dp,
                )
                .size(120.dp)
                .align(Alignment.Center),

        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (pokemonInfo is Resource.Success) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(pokemonInfo.data?.sprites?.frontDefault)
                        .crossfade(1000)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemonInfo.data?.name,
                    modifier = Modifier
                        .offset(y = topPadding.dp)
                        .size(pokemonImageSize.dp),
                )
            }
        }
    }
}
