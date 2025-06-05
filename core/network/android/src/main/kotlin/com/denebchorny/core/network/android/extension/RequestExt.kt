package com.denebchorny.core.network.android.extension

import okhttp3.Request
import retrofit2.Invocation

/**
 * Extension function for OkHttp `Request` to retrieve an annotation of a specified type
 * from the request's associated method.
 */
inline fun <reified T : Annotation> Request.getAnnotation(): T? {
    return tag(Invocation::class.java)
        ?.method()
        ?.getAnnotation(T::class.java)
}

/**
 * Extension function for OkHttp `Request` to check if a specific annotation is present
 * on the request's associated method.
 */
inline fun <reified T : Annotation> Request.hasAnnotation(): Boolean {
    return this.tag(Invocation::class.java)
        ?.method()
        ?.getAnnotation(T::class.java) != null
}
