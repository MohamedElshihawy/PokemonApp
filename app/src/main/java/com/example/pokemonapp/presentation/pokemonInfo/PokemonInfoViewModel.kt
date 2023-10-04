package com.example.pokemonapp.presentation.pokemonInfo

import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.remote.RemoteRepositoryImpl
import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.utills.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val repository: RemoteRepositoryImpl,
) :
    ViewModel() {

    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        return repository.getPokemonInfo(name)
    }
}
