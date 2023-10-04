package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonapp.presentation.pokemonInfo.PokemonInfoScreen
import com.example.pokemonapp.presentation.pokemonList.PokemonListScreen
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.utills.Screen
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomePage.route,
                    ) {
                        composable(route = Screen.HomePage.route) {
                            PokemonListScreen(
                                navController = navController,
                            )
                        }

                        composable(
                            route = "${Screen.DetailsPage.route}/{name}/{dominant_color}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                },
                                navArgument("dominant_color") {
                                    type = NavType.IntType
                                },
                            ),
                        ) { backStack ->

                            val dominantColor = remember {
                                val color = backStack.arguments?.getInt("dominant_color")
                                color?.let { Color(it) } ?: Color.White
                            }

                            val name = remember {
                                backStack.arguments?.getString("name")
                            }

                            PokemonInfoScreen(
                                navController = navController,
                                dominantColor = dominantColor,
                                pokemonName = name?.lowercase(Locale.ROOT)!!,
                            )
                        }
                    }
                }
            }
        }
    }
}
