package com.example.pokemonapp.presentation.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokemonapp.data.models.PokemonListItem
import com.example.pokemonapp.data.remote.RemoteRepositoryImpl
import com.example.pokemonapp.utills.Constants.PAGE_SIZE
import com.example.pokemonapp.utills.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: RemoteRepositoryImpl,
) : ViewModel() {

    private var currentPageCount = 0

    private var _pokemonPageList = mutableStateOf<List<PokemonListItem>>(listOf())
    private var _isLoading = mutableStateOf(false)
    private var _errorOccurred = mutableStateOf("")
    private var _endOfPagesReached = mutableStateOf(false)
    private var _isSearching = mutableStateOf(false)
    private var _canStartSearching = mutableStateOf(true)

    val pokemonPageList = _pokemonPageList
    val isLoading = _isLoading
    val errorOccurred = _errorOccurred
    val endOfPagesReached = _endOfPagesReached
    val pokemonList = mutableListOf<PokemonListItem>()
    private var cachedPokemonList = listOf<PokemonListItem>()
    val isSearching = _isSearching

    init {
        loadPaginatedData()
    }

    fun searchCachedPokemons(query: String) {
        val itemsToSearch = if (_canStartSearching.value) {
            pokemonPageList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonPageList.value = cachedPokemonList
                _isSearching.value = false
                _canStartSearching.value = true
                return@launch
            }

            val results = itemsToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                    it.number.toString() == query.trim()
            }
            if (_canStartSearching.value) {
                cachedPokemonList = pokemonPageList.value
                _canStartSearching.value = false
                _isSearching.value = true
            }
            pokemonPageList.value = results
        }
    }

    fun loadPaginatedData() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getPokemonList(
                limit = PAGE_SIZE,
                offset = currentPageCount * PAGE_SIZE,
            )

            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _endOfPagesReached.value = result.data!!.count <= currentPageCount * PAGE_SIZE
                    val newPokemonList = result.data.results.mapIndexed { _, pokemon ->
                        val pokemonNumber = if (pokemon.url.endsWith("/")) {
                            pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            pokemon.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites" +
                                "/pokemon/other/official-artwork/shiny/$pokemonNumber.png"
                        PokemonListItem(
                            pokemon.name.replaceFirstChar {
                                if (it.isLowerCase()) {
                                    it.titlecase(
                                        Locale.ROOT,
                                    )
                                } else {
                                    it.toString()
                                }
                            },
                            url,
                            pokemonNumber.toInt(),
                        )
                    }
                    currentPageCount++
                    _isLoading.value = false
                    _errorOccurred.value = ""
                    pokemonList.addAll(newPokemonList)
                    _pokemonPageList.value = pokemonList
                }

                is Resource.Error -> {
                    _errorOccurred.value = result.message.toString()
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val img = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(img).generate { palette ->
            palette?.dominantSwatch?.rgb?.let {
                onFinish(Color(it))
            }
        }
    }
}
