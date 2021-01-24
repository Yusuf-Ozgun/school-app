package com.example.school.network

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.school.domain.Record
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException


private const val BASE_URL = "https://data.stad.gent"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

var okHttpClient = OkHttpClient.Builder()
    .addInterceptor(object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response? {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)

            // todo deal with the issues the way you need to
            if (response.code() == 500) {
                print("500")
                return response
            }
            return response
        }
    })
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface SchoolApiService {
    @GET("/api/records/1.0/search/?dataset=locaties-basisscholen-gent&q=")
    fun getSchoolAsync(@Query("q") query : String):
            Deferred<Record>
}


object SchoolApi {
    val retrofitService : SchoolApiService by lazy { retrofit.create(SchoolApiService::class.java) }
}
