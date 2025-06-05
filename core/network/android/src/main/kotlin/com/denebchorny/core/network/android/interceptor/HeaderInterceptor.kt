package com.denebchorny.core.network.android.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.Locale

/**
 * Adds custom headers to every HTTP request.
 * This interceptor appends headers such as the device's language and the brand name,
 * to all outgoing requests.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Accept-Language", Locale.getDefault().language)
        builder.addHeader("Agent-Brand", "Deneb Asecas")

        return chain.proceed(builder.build())
    }
}