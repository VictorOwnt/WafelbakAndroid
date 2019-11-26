package be.scoutswondelgem.wafelbak.injection.module

import android.content.Context
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.WafelbakDatabase
import be.scoutswondelgem.wafelbak.database.dataAccessObjects.UserDao
import be.scoutswondelgem.wafelbak.util.Constants.BASE_URL
//import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    @Singleton
    @Provides
    internal fun provideUserDataSource(context: Context): UserDao {
        val database = WafelbakDatabase.getInstance(context)
        return database.userDao()
    }

    @Singleton
    @Provides
    internal fun provideWafelbakApi(retrofit: Retrofit): WafelbakApi {
        return retrofit.create(WafelbakApi::class.java)
    }

    @Singleton
    @Provides
    internal fun provideRetrofitInterface(): Retrofit {
        // Used for Retrofit/OkHttp debugging.
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        /*val moshi = Moshi.Builder()
            .add(DateAdapter())
            .build()*/

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            //.addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}