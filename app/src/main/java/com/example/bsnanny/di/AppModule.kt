package com.example.bsnanny.di

import com.example.bsnanny.BuildConfig
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.retrofit.ApiHelperImpl
import com.example.bsnanny.retrofit.ApiInterface
import com.example.bsnanny.retrofit.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule{

        @Provides
        fun provideBaseUrl() = Constants.BASE_URL

        @Singleton
        @Provides
        fun provideOkHttpClient() = if (BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }else{
            OkHttpClient
                .Builder()
                .build()
        }

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

        @Provides
        @Singleton
        fun provideApiHelper(apiHelper: ApiInterface): ApiHelper {
            return ApiHelperImpl(apiHelper)
        }

    }