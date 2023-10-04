package com.example.pokemonapp.data.remote

import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.data.remote.response.PokemonList
import com.example.pokemonapp.repositories.PokemonApi
import com.example.pokemonapp.utills.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RemoteRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
) {

    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 20,
    ): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(
                data = null,
                message = "An Error Occurred",
            )
        }
        return Resource.Success(data = response)
    }

    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e: Exception) {
            return Resource.Error(
                data = null,
                message = "An Error Occurred",
            )
        }
        return Resource.Success(data = response)
    }
}
