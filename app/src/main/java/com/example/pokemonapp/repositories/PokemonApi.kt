package com.example.pokemonapp.repositories

import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.data.remote.response.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PokemonList

    @GET("pokemon/{pokemon_name}")
    suspend fun getPokemonInfo(
        @Path("pokemon_name") pokemonName: String,
    ): Pokemon
}
