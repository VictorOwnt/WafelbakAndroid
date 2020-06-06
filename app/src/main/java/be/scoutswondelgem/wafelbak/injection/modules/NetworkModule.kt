package be.scoutswondelgem.wafelbak.injection.modules

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import be.scoutswondelgem.wafelbak.api.WafelbakApiClient
import be.scoutswondelgem.wafelbak.util.Constants
import be.scoutswondelgem.wafelbak.api.adapters.DateAdapter
import be.scoutswondelgem.wafelbak.api.adapters.DeliveryStatusAdapter
import be.scoutswondelgem.wafelbak.api.adapters.DeliveryTimeAdapter
import be.scoutswondelgem.wafelbak.api.adapters.UserRoleAdapter
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

val networkModule = module {
    factory { provideOkHttpClient(get()) }
    factory { provideWafelbakApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideOkHttpClient(context: Context): OkHttpClient {
    fun checkNetworkState(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other devices who are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
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
            request = if (checkNetworkState(context))
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
    val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(DeliveryStatusAdapter())
        .add(DeliveryTimeAdapter())
        .add(UserRoleAdapter())
        .build()

    return Retrofit.Builder().baseUrl(Constants.API_URL).client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi)).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(
            Schedulers.io())).build()
}

fun provideWafelbakApi(retrofit: Retrofit): WafelbakApiClient {
    return retrofit.create(WafelbakApiClient::class.java)
}