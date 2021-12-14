package com.example.pixarbayapp.di

import android.content.Context
import com.example.pixarbayapp.api.ApiInterceptor
import com.example.pixarbayapp.api.ImagesApi
import com.example.pixarbayapp.api.ImagesApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Object makes the generated data code more efficient
//SingletonComponent ensures the modules can be available throughout the application's lifecycle
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //We use Singleton because we want to have the same instance all over the app
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        //initialize interceptor
        val authInterceptor = ApiInterceptor()
        //add interceptor
        val client = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }
}