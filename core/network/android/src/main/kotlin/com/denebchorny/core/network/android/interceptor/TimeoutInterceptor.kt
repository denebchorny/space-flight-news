package com.denebchorny.core.network.android.interceptor
import com.denebchorny.core.network.android.extension.getAnnotation
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * A custom annotation for specifying timeout values for HTTP requests.
 *
 * This annotation can be applied to functions to define connection, read, and write
 * timeout values in milliseconds. If any timeout value is set to its default (-1),
 * the interceptor will use the existing timeout configuration.
 *
 * @property conn The connection timeout in milliseconds. Default is -1 (use existing timeout).
 * @property read The read timeout in milliseconds. Default is -1 (use existing timeout).
 * @property write The write timeout in milliseconds. Default is -1 (use existing timeout).
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Timeout(
    val conn: Int = -1,
    val read: Int = -1,
    val write: Int = -1
)

/**
 * An OkHttp interceptor that dynamically sets timeout values for HTTP requests
 * based on the presence of the `@Timeout` annotation.
 *
 * The interceptor reads the timeout values specified in the `@Timeout` annotation
 * and applies them to the request. If no annotation is present, or if any timeout
 * value is set to its default (-1), the interceptor uses the existing timeout values.
 */
class TimeoutInterceptor : Interceptor {

    /**
     * Intercepts HTTP requests and applies custom timeout settings if the `@Timeout`
     * annotation is present on the request.
     *
     * @param chain The chain of interceptors in the OkHttp pipeline.
     * @return The HTTP response after applying the custom timeout settings.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request() // Retrieve the original HTTP request.
        val annotation = request.getAnnotation<Timeout>() ?: return chain.proceed(request)

        val cTimeout = annotation.conn.takeIf { it >= 0 } ?: chain.connectTimeoutMillis()
        val rTimeout = annotation.read.takeIf { it >= 0 } ?: chain.readTimeoutMillis()
        val wTimeout = annotation.write.takeIf { it >= 0 } ?: chain.writeTimeoutMillis()

        return chain.withConnectTimeout(cTimeout, TimeUnit.MILLISECONDS)
            .withReadTimeout(rTimeout, TimeUnit.MILLISECONDS)
            .withWriteTimeout(wTimeout, TimeUnit.MILLISECONDS)
            .proceed(request)
    }
}
