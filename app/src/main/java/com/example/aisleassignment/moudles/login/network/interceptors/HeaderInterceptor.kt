package com.example.aisleassignment.moudles.login.network.interceptors

import android.content.Context
import com.example.aisleassignment.moudles.core.utils.sharedPref.SharedPref
import com.example.aisleassignment.moudles.core.utils.sharedPref.SharedPrefConstants.AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class HeaderInterceptor @Inject constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val auth = SharedPref.getString(context, AUTH_TOKEN)
        if (auth.isNotEmpty()) {
            requestBuilder.removeHeader("Authorization")
            requestBuilder.addHeader("Authorization", auth)
        }
        return chain.proceed(requestBuilder.build());
    }
}