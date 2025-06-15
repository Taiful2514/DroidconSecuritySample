package com.example.droidconsecuritysample.ui.common.di.modules

import com.example.droidconsecuritysample.BuildConfig
import com.example.droidconsecuritysample.data.network.ApiHelper
import com.example.droidconsecuritysample.data.network.ApiHelperImpl
import com.example.droidconsecuritysample.data.network.ApiService
import com.example.droidconsecuritysample.ui.common.di.ApplicationScope
import com.example.droidconsecuritysample.util.Constant.BASE_URL
import com.example.droidconsecuritysample.util.Constant.OKHTTP_SSL_PIN
import com.example.droidconsecuritysample.util.Constant.PINNING_URL
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author taiful
 * @since 14/6/25
 */
@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun apiHelper(apiService: ApiService): ApiHelper {
        return ApiHelperImpl(apiService)
    }

    @Provides
    @ApplicationScope
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @ApplicationScope
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @ApplicationScope
    fun okhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        builder.certificatePinner(certificatePinner)
        return builder.build()
    }

    @Provides
    @ApplicationScope
    fun interceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @ApplicationScope
    fun certificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(PINNING_URL, OKHTTP_SSL_PIN)
            .build()
    }
}