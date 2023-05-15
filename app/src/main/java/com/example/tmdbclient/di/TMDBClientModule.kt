package com.example.tmdbclient.di

import com.example.tmdbclient.data.remote.TMDBApi
import com.example.tmdbclient.data.repository.remote.popularmovie.PopularMovieRepository
import com.example.tmdbclient.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TMDBClientModule {

    @Singleton
    @Provides
    fun provideHttpInterceptor(): Interceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .addQueryParameter("language", Constants.LANGUAGE)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideTMDBApi(retrofit: Retrofit): TMDBApi =
        retrofit.create(TMDBApi::class.java)

    @Singleton
    @Provides
    fun providePopularMovieRepository(tmdbApi: TMDBApi): PopularMovieRepository {
        return PopularMovieRepository(tmdbApi)
    }
}