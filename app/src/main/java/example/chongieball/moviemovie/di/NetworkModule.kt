package example.chongieball.moviemovie.di

import example.chongieball.moviemovie.BuildConfig
import example.chongieball.moviemovie.data.source.remote.Api
import example.chongieball.moviemovie.data.source.remote.RemoteSource
import example.chongieball.moviemovie.util.Constant
import example.chongieball.moviemovie.data.source.remote.RequestInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .connectTimeout(Constant.Network.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.Network.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(RequestInterceptor())
            .build()
    }

    single {
        RemoteSource(get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get<OkHttpClient>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(Api::class.java)
    }
}