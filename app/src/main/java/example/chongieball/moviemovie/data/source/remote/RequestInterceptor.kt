package example.chongieball.moviemovie.data.source.remote

import example.chongieball.moviemovie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    private val API_KEY = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter(API_KEY, BuildConfig.API_KEY).build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}