package com.jovita.harrypotter.network


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.jovita.harrypotter.model.MovieCharacter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.File

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<ArrayList<MovieCharacter>>

    companion object {
        private const val BASE_URL = "https://hp-api.onrender.com/api/"


        fun create(context: Context): ApiService {

            //Setting up offline services
            val cacheSize = 10 * 1024 * 1024L // 10 MB
            val cacheDir = File(context.cacheDir, "http_cache")
            val cache = Cache(cacheDir, cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    // Checking if the device is online
                    request = if (context.hasNetwork())
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 60)
                            .build()
                    else
                        request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ).build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)// adding caching opton with okhttpclient
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

fun Context.hasNetwork(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

