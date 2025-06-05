package com.denebchorny.core.common.jvm.result

/**
 * A type alias representing a [Result] where success has no data, but an error does.
 */
typealias EmptyResult<E> = Result<E, Unit>

/**
 * A sealed class representing a result that can either be a success or an error.
 *
 * @param E The type of the error.
 * @param S The type of the success data.
 */
sealed class Result<out E, out S> {

    /**
     * Represents the error case of the [Result] class.
     *
     * @param error The error value of type [E].
     */
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    /**
     * Represents the success case of the [Result] class.
     *
     * @param data The success value of type [S].
     */
    data class Success<out S>(val data: S) : Result<Nothing, S>()

    /**
     * Creates an [Error] instance.
     *
     * @param value The error value to wrap.
     * @return An [Error] instance containing the provided value.
     */
    fun <E> error(value: E) = Error(value)

    /**
     * Creates a [Success] instance.
     *
     * @param value The success value to wrap.
     * @return A [Success] instance containing the provided value.
     */
    fun <S> success(value: S) = Success(value)

    /**
     * Checks if the result is an error.
     *
     * @return `true` if the result is an [Error], `false` otherwise.
     */
    val isError: Boolean get() = this is Error<E>

    /**
     * Checks if the result is a success.
     *
     * @return `true` if the result is a [Success], `false` otherwise.
     */
    val isSuccess: Boolean get() = this is Success<S>

    /**
     * Applies a function to the error or success value depending on the type of result.
     *
     * @param onError The function to apply if this is an [Error].
     * @param onSuccess The function to apply if this is a [Success].
     * @return The result of the applied function.
     */
    fun <T> fold(
        onError: (E) -> T,
        onSuccess: (S) -> T,
    ): T = when (this) {
        is Error -> onError(error)
        is Success -> onSuccess(data)
    }

    suspend fun <T> foldSuspend(
        onError: suspend (E) -> T,
        onSuccess: suspend (S) -> T,
    ): T = when (this) {
        is Error -> onError(error)
        is Success -> onSuccess(data)
    }
}

/**
 * Composes two functions, applying the second to the result of the first.
 *
 * @param f A function to apply after the current function.
 * @return A composed function.
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Transforms the success value of a [Result] using a mapping function.
 *
 * If the result is an [Result.Error], it is returned unchanged.
 *
 * @param fn The mapping function to apply to the success value.
 * @return A new [Result] with the transformed success value.
 */
fun <T, E, S> Result<E, S>.flatMap(fn: (S) -> Result<E, T>): Result<E, T> =
    when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> fn(data)
    }

/**
 * Maps the success value of the [Result] using a function.
 *
 * @param fn The function to apply to the success value.
 * @return A [Result] with the mapped success value.
 */
fun <T, E, S> Result<E, S>.map(fn: (S) -> (T)): Result<E, T> = this.flatMap(fn.c(::success))

/**
 * Maps the error value of the [Result] using a function.
 *
 * @param fn The function to apply to the error value.
 * @return A [Result] with the mapped error value.
 */
fun <T, E, S> Result<E, S>.mapError(fn: (E) -> T): Result<T, S> {
    return when (this) {
        is Result.Error -> Result.Error(fn(error))
        is Result.Success -> Result.Success(data)
    }
}

/**
 * Returns the success value or the provided default if this is an [Result.Error].
 *
 * @param value The default value to return if this is an [Result.Error].
 * @return The success value or the provided default.
 */
fun <E, S> Result<E, S>.successOr(value: S): S =
    when (this) {
        is Result.Error -> value
        is Result.Success -> data
    }

/**
 * Returns the error value or the provided default if this is a [Result.Success].
 *
 * @param value The default value to return if this is a [Result.Success].
 * @return The error value or the provided default.
 */
fun <E, S> Result<E, S>.errorOr(value: S): S =
    when (this) {
        is Result.Error -> value
        is Result.Success -> data
    }


/**
 * Returns the success value or null if this is an [Result.Error].
 *
 * @return The success value or null.
 */
fun <E, S> Result<E, S>.successOrNull(): S? =
    when (this) {
        is Result.Error -> null
        is Result.Success -> data
    }

/**
 * Returns the error value or null if this is a [Result.Success].
 *
 * @return The error value or null.
 */
fun <E, S> Result<E, S>.errorOrNull(): E? =
    when (this) {
        is Result.Error -> error
        is Result.Success -> null
    }

/**
 * Performs the given action if this is an [Result.Error].
 *
 * @param fn The action to perform on the error value.
 * @return The original [Result].
 */
fun <E, S> Result<E, S>.onError(fn: (error: E) -> Unit): Result<E, S> =
    this.apply { if (this is Result.Error) fn(error) }

/**
 * Executes the given suspend function if the [Result] is an [Result.Error].
 *
 * @param fn The suspend function to execute with the error value.
 * @return The original [Result] for chaining.
 */
suspend fun <E, S> Result<E, S>.onErrorSuspend(fn: suspend (error: E) -> Unit): Result<E, S> {
    if (this is Result.Error) {
        fn(error)
    }
    return this
}

/**
 * Performs the given action if this is a [Result.Success].
 *
 * @param fn The action to perform on the success value.
 * @return The original [Result].
 */
fun <E, S> Result<E, S>.onSuccess(transform: (success: S) -> Unit): Result<E, S> =
    this.apply { if (this is Result.Success) transform(data) }

/**
 * Executes the given suspend function if the [Result] is a [Success].
 *
 * @param fn The suspend function to execute with the success data.
 * @return The original [Result] for chaining.
 */
suspend fun <E, S> Result<E, S>.onSuccessSuspend(transform: suspend (S) -> Unit): Result<E, S> {
    if (this is Result.Success) {
        transform(data)
    }
    return this
}

/**
 * Extension function to flatMap a [Result] with a suspend function.
 *
 * @param fn The suspend function to apply if the result is [Success].
 * @return A new [Result] after applying the function.
 */
suspend fun <E, S, T> Result<E, S>.flatMapSuspend(transform: suspend (S) -> Result<E, T>): Result<E, T> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> transform(this.data)
    }
}

suspend fun <E, F, V> Result<E, V>.flatMapErrorSuspend(transform: suspend (E) -> Result<F, V>): Result<F, V> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> transform(this.error)
    }
}


/**
 * Converts a [Result] into an `EmptyResult` by ignoring its success data.
 *
 * @return An `EmptyResult` containing the error value.
 */
fun <T, E> Result<E, T>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}


