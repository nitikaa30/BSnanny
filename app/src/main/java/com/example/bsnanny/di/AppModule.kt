package com.example.bsnanny.di

import com.example.bsnanny.BuildConfig
import com.example.bsnanny.authToken.AuthUser
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.retrofit.ApiHelperImpl
import com.example.bsnanny.retrofit.ApiInterface
import com.example.bsnanny.utils.Constants
import com.example.bsnanny.utils.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import okio.Timeout
import java.util.concurrent.TimeUnit

@Module
    @InstallIn(SingletonComponent::class)
    object AppModule{

        @Provides
        fun provideBaseUrl() = Constants.BASE_URL

        @Singleton
        @Provides
        fun provideOkHttpClient(interceptor: NetworkInterceptor) = if (BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }else{
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
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

        @Provides
        @Singleton
        fun providesAuthToken(): AuthUser {
            return AuthUser()
        }

    @Singleton
    @Provides
    fun provideMeFunctionality(): ABC {
        return ABC()
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(authUser: AuthUser): NetworkInterceptor {
        return NetworkInterceptor(authUser.getUser()?.token.toString())
    }


    }
class ABC {

    init {
     abc()
    }
    fun abc() {
        println("Dependency injection success")

    }
}