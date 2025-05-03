package com.normalnywork.plants.data.di

import com.normalnywork.plants.data.api.AuthService
import com.normalnywork.plants.data.api.auth.AuthInterceptor
import com.normalnywork.plants.data.api.auth.PrefsTokenStoreImpl
import com.normalnywork.plants.data.api.auth.TokenStore
import com.normalnywork.plants.utils.Const
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

val networkModule = module {

    single {
        val mediaType = "application/json; charset=UTF8".toMediaType()

        Retrofit.Builder()
            .baseUrl(Const.API_ENDPOINT)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(get<Json>().asConverterFactory(mediaType))
            .build()
    }

    single { get<Retrofit>().create<AuthService>() }

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    single<TokenStore> { PrefsTokenStoreImpl() }
}