package com.example.jetpackcomposemedicines.di

import com.example.jetpackcomposemedicines.data.network.MedicinesApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://cima.aemps.es/cima/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMedicinesApiClient(retrofit: Retrofit):MedicinesApiClient{
        return retrofit.create(MedicinesApiClient::class.java)
    }
}