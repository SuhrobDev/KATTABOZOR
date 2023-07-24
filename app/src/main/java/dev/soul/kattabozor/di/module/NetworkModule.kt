package dev.soul.kattabozor.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.soul.kattabozor.data.remote.MainApi
import dev.soul.kattabozor.data.repository.MainRepositoryImpl
import dev.soul.kattabozor.domain.repository.MainRepository
import dev.soul.kattabozor.presentation.utils.SharedPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideEncryptedSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreference =
        SharedPreference.getInstance(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        sharedPreference: SharedPreference
    ): OkHttpClient {
        val chuckInterceptor = ChuckerInterceptor.Builder(context)
            .maxContentLength(500_000L)
            .alwaysReadResponseBody(true)
            .build()
        val builder = OkHttpClient.Builder()
            .addInterceptor(chuckInterceptor)
            .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${sharedPreference.token}"
                    )
                    .addHeader("lang", sharedPreference.lang)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            })
            .build()
        return builder
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonGsonConverterFactory: GsonConverterFactory,
        builder: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.kattabozor.uz/")
            .client(builder)
            .addConverterFactory(gsonGsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainRepository(mainService: MainApi): MainRepository {
        return MainRepositoryImpl(mainService)
    }

    @Singleton
    @Provides
    fun provideMainService(retrofit: Retrofit): MainApi =
        retrofit.create(MainApi::class.java)
}