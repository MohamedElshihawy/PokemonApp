package com.example.pokemonapp.utills

sealed class Screen(val route: String) {

    object HomePage : Screen(route = "HomePage")
    object DetailsPage : Screen(route = "DetailsPage")
}
