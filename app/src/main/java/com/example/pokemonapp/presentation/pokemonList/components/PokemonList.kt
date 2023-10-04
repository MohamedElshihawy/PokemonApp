package com.example.pokemonapp.presentation.pokemonList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemonapp.presentation.pokemonList.PokemonListViewModel

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val scrollState = rememberLazyGridState()

    val pokemonList = remember { viewModel.pokemonPageList }
    val isLoading = remember { viewModel.isLoading }
    val errorOccurred = remember { viewModel.errorOccurred }
    val endOfPageReached = remember { viewModel.endOfPagesReached }
    val isSearching = remember { viewModel.isSearching }

    fun LazyGridState.isScrolledToEnd() =
        layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

    val endOfListReached = remember {
        derivedStateOf { scrollState.isScrolledToEnd() }
    }

    if (endOfListReached.value && !endOfPageReached.value && !isLoading.value && !isSearching.value) {
        LaunchedEffect(key1 = true) {
            viewModel.loadPaginatedData()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 12.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        state = scrollState,
    ) {
        items(pokemonList.value) { item ->

            PokemonListItemScreen(
                navController = navController,
                pokemonListItem = item,
                modifier = Modifier,
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (errorOccurred.value.isNotEmpty()) {
            RetryLoading(errorMessage = errorOccurred.value) {
                viewModel.loadPaginatedData()
            }
        }
    }
}
