package com.abdulrahman.contactlistapp.di

import com.abdulrahman.contactlistapp.data.remote.UsersWebService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebservicesModule {
    private const val BASE_URL = "https://randomuser.me"

    val get = module {
        single { coroutineAdapterFactory() }
        single { httpLoggingInterceptor() }
        single { HeadersInterceptor() }
        single { okHttpClient(get(), get()) }
        single { gsonFactory() }
        single { getWebserviceInstance(get(), get(), get()) }
        single { get<Retrofit>().create(UsersWebService::class.java) }
    }

    private fun getWebserviceInstance(
        okHttpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
        coroutine: CoroutineCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonFactory)
        .addCallAdapterFactory(coroutine)
        .build()

    private fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: HeadersInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()
    }


    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    private fun coroutineAdapterFactory() = CoroutineCallAdapterFactory.invoke()


    private fun gsonFactory() = GsonConverterFactory.create()


    class HeadersInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val request = original.newBuilder()
                .build()

            return chain.proceed(request)
        }
    }
}