package com.example.pokemonapp.presentation.pokemonList.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.data.models.PokemonListItem
import com.example.pokemonapp.presentation.pokemonList.PokemonListViewModel
import com.example.pokemonapp.ui.theme.RobotoCondensed
import com.example.pokemonapp.utills.Screen
import java.lang.Float.min

@Composable
fun PokemonListItemScreen(
    navController: NavController,
    pokemonListItem: PokemonListItem,
    modifier: Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val defaultDominantColor = Color.White
    val isLoadingImage = remember {
        mutableStateOf(false)
    }
    val dominantColor = remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp)
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor.value,
                        defaultDominantColor,
                    ),
                ),
            )
            .clickable {
                navController.navigate(
                    Screen.DetailsPage.route +
                        "/${pokemonListItem.name}" +
                        "/${dominantColor.value.toArgb()}",
                )
            },

    ) {
        Column {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(pokemonListItem.imageUrl)
                    .build(),
                onSuccess = {
                    viewModel.getDominantColor(it.result.drawable) { color ->
                        dominantColor.value = color
                        isLoadingImage.value = false
                    }
                },
                onLoading = {
                    isLoadingImage.value = true
                },
                onError = {
                    isLoadingImage.value = false
                },
            )

            val state = painter.state
            val transition by animateFloatAsState(
                targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
                label = "",
            )

            Image(
                painter = painter,
                contentDescription = pokemonListItem.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
                    .scale(.8f + (.2f * transition))
                    .rotate(
                        (1f - transition) * 360,
                    )
                    .alpha(min(1f, transition / .2f)),
            )

            Text(
                text = pokemonListItem.name,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (isLoadingImage.value) {
            Box(
                contentAlignment = Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
