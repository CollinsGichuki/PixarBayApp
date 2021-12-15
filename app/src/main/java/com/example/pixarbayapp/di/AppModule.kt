package com.example.pixarbayapp.di

import android.app.Application
import androidx.room.Room
import com.example.pixarbayapp.api.ApiInterceptor
import com.example.pixarbayapp.api.ImagesApi
import com.example.pixarbayapp.api.ImagesApi.Companion.BASE_URL
import com.example.pixarbayapp.data.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    //This method provides an instance of the retrofit Interface throughout the application
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

    //This provides the ImageApi Interface throughout the application
    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }

    //This method provides an instance of the database throughout the application
    @Provides
    @Singleton
    fun provideImageDatabase(app: Application): ImageDatabase {
        return Room.databaseBuilder(app, ImageDatabase::class.java, "image_database").build()
    }
}