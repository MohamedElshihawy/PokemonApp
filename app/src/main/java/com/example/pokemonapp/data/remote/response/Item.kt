package com.example.pokemonapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
