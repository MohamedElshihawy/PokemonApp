package com.example.pokemonapp.di

import com.example.pokemonapp.data.remote.RemoteRepositoryImpl
import com.example.pokemonapp.repositories.PokemonApi
import com.example.pokemonapp.utills.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApi(): PokemonApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(httpClient.build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(api: PokemonApi) = RemoteRepositoryImpl(api)
}
