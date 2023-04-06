package com.dicoding.core.data.apis

import android.content.SharedPreferences
import androidx.viewbinding.BuildConfig
import com.dicoding.core.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
class ApiConfig {

    var BASE_URLS = "https://api.themoviedb.org/3/"

//    @Singleton
//    @Provides
//    fun provideApi(): ApiService {
//        val loggingInterceptor = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        } else {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//        }
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client())
//            .build()
//
//        return retrofit.create(ApiService::class.java)
//    }
//
//    private fun client(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .readTimeout(50L, TimeUnit.SECONDS)
//            .connectTimeout(50L, TimeUnit.SECONDS)
//            .writeTimeout(50L, TimeUnit.SECONDS)
//            .addInterceptor {
//                val original = it.request()
//                val requestBuilder = original.newBuilder()
////                requestBuilder.header("Authorization", "Bearer " + sharedPreference.getString(
////                    "token_data", ""))
//
//                val request = requestBuilder.method(original.method, original.body).build()
//                return@addInterceptor it.proceed(request)
//            }
//            .also { client ->
//                if (BuildConfig.DEBUG) {
//                    val logging = HttpLoggingInterceptor()
//                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//                    client.addInterceptor(logging)
//                }
//            }
//            .build()
//    }

    companion object {

        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}