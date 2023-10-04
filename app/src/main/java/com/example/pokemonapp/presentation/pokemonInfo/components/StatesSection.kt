package com.example.pokemonapp.presentation.pokemonInfo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.data.remote.response.Pokemon
import com.example.pokemonapp.utills.parseStatToAbbr
import com.example.pokemonapp.utills.parseStatToColor

@Composable
fun StatesItem(
    stateName: String,
    stateValue: Int,
    stateMaxValue: Int,
    stateColor: Color,
    height: Dp = 36.dp,
    animDuration: Int = 800,
    animDelay: Int = 0,
) {
    val animStarted = remember {
        mutableStateOf(false)
    }
    val animValue = animateFloatAsState(
        targetValue =
        if (animStarted.value) {
            stateValue / stateMaxValue.toFloat()
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay,
        ),
        label = "",
    )

    LaunchedEffect(key1 = true) {
        animStarted.value = true
    }

    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color.LightGray
                } else {
                    Color.DarkGray
                },
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .background(stateColor)
                .fillMaxWidth(animValue.value)
                .padding(horizontal = 8.dp)
                .clip(CircleShape),
        ) {
            Text(
                text = stateName,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = (animValue.value * stateMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun StatesSection(
    pokemon: Pokemon,
    stateAnimDelay: Int = 100,
) {
    val maxStateValue = remember {
        pokemon.stats.maxOf {
            it.baseStat
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Base Stats:",
            fontSize = 20.sp,
        )

        Spacer(
            modifier = Modifier.height(4.dp),
        )

        for (i in pokemon.stats.indices) {
            val state = pokemon.stats[i]
            StatesItem(
                stateName = parseStatToAbbr(state),
                stateValue = state.baseStat,
                stateMaxValue = maxStateValue,
                stateColor = parseStatToColor(state),
                animDelay = i * stateAnimDelay,
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
