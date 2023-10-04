package com.example.pokemonapp.presentation.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemonapp.R
import com.example.pokemonapp.presentation.pokemonList.components.PokemonList
import com.example.pokemonapp.presentation.pokemonList.components.SearchBar

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .align(CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(12.dp))

            val searchState = remember {
                mutableStateOf(
                    SearchBarState(
                        text = "",
                        isHintDisplayed = true,
                        hint = "Enter Name",
                    ),
                )
            }
            SearchBar(
                modifier = Modifier.padding(horizontal = 12.dp),
                hint = searchState.value.hint,
                text = searchState.value.text,
                isHintDisplayed = searchState.value.isHintDisplayed,
                onSearch = {
                    searchState.value = searchState.value.copy(
                        text = it,
                    )
                    viewModel.searchCachedPokemons(it)
                },
                onFocusChanged = {
                    searchState.value = searchState.value.copy(
                        isHintDisplayed = !it.isFocused,
                    )
                },
            )

            Spacer(modifier = Modifier.height(12.dp))

            PokemonList(navController)
        }
    }
}
