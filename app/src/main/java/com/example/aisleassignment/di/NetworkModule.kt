package com.example.aisleassignment.di

import android.content.Context
import com.example.aisleassignment.moudles.core.utils.EndPoints
import com.example.aisleassignment.moudles.login.convertor.LoginRepositoryDataConvertor
import com.example.aisleassignment.moudles.login.network.interceptors.HeaderInterceptor
import com.example.aisleassignment.moudles.login.network.services.LoginApiService
import com.example.aisleassignment.moudles.login.repository.LoginRepository
import com.example.aisleassignment.moudles.login.repository.LoginRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(@ApplicationContext context: Context): HeaderInterceptor {
        return HeaderInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .baseUrl(EndPoints.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService =
        retrofit.create(LoginApiService::class.java)

    @Provides
    @Singleton
    fun provideLoginApiConvertor(): LoginRepositoryDataConvertor = LoginRepositoryDataConvertor()

    @Provides
    @Singleton
    fun provideQuizRepository(
        loginApiService: LoginApiService,
        loginRepositoryDataConvertor: LoginRepositoryDataConvertor
    ): LoginRepository {
        return LoginRepositoryImpl(loginApiService, loginRepositoryDataConvertor)
    }
}