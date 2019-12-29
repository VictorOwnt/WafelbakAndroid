package be.scoutswondelgem.wafelbak.injection.modules

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.util.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { provideOkHttpClient(get()) }
    factory { provideWafelbakApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideOkHttpClient(context: Context): OkHttpClient {
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
        // Specify the cache we created earlier.
        .cache(myCache)
        // Add an Interceptor to the OkHttpClient.
        .addInterceptor { chain ->

            // Get the request from the chain.
            var request = chain.request()

            /*
            *  Leveraging the advantage of using Kotlin,
            *  we initialize the request and change its header depending on whether
            *  the device is connected to Internet or not.
            */
            request = if (hasNetwork(context)!!)
            /*
            *  If there is Internet, get the cache that was stored 5 seconds ago.
            *  If the cache is older than 5 seconds, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
            /*
            *  If there is no Internet, get the cache that was stored 7 days ago.
            *  If the cache is older than 7 days, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-stale' attribute is responsible for this behavior.
            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
            */
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            // End of if-else statement

            // Add the modified request to the chain.
            chain.proceed(request)
        }.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.API_URL).client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun provideWafelbakApi(retrofit: Retrofit): WafelbakApi {
    return retrofit.create(WafelbakApi::class.java)
}