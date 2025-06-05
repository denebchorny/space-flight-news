package com.denebchorny.core.common.jvm.result

/**
 * A type alias representing a [Outcome] where success has no data, but an error does.
 */
typealias EmptyResult<E> = Outcome<E, Unit>

/**
 * A sealed class representing a result that can either be a success or an error.
 *
 * @param E The type of the error.
 * @param S The type of the success data.
 */
sealed class Outcome<out E, out S> {

    /**
     * Represents the error case of the [Outcome] class.
     *
     * @param error The error value of type [E].
     */
    data class Error<out E>(val error: E) : Outcome<E, Nothing>()

    /**
     * Represents the success case of the [Outcome] class.
     *
     * @param data The success value of type [S].
     */
    data class Success<out S>(val data: S) : Outcome<Nothing, S>()

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
 * Transforms the success value of a [Outcome] using a mapping function.
 *
 * If the result is an [Outcome.Error], it is returned unchanged.
 *
 * @param fn The mapping function to apply to the success value.
 * @return A new [Outcome] with the transformed success value.
 */
fun <T, E, S> Outcome<E, S>.flatMap(fn: (S) -> Outcome<E, T>): Outcome<E, T> =
    when (this) {
        is Outcome.Error -> Outcome.Error(error)
        is Outcome.Success -> fn(data)
    }

/**
 * Maps the success value of the [Outcome] using a function.
 *
 * @param fn The function to apply to the success value.
 * @return A [Outcome] with the mapped success value.
 */
fun <T, E, S> Outcome<E, S>.map(fn: (S) -> (T)): Outcome<E, T> = this.flatMap(fn.c(::success))


/**
 * Maps the error value of the [Outcome] using a function.
 *
 * @param fn The function to apply to the error value.
 * @return A [Outcome] with the mapped error value.
 */
fun <T, E, S> Outcome<E, S>.mapError(fn: (E) -> T): Outcome<T, S> {
    return when (this) {
        is Outcome.Error -> Outcome.Error(fn(error))
        is Outcome.Success -> Outcome.Success(data)
    }
}

/**
 * Returns the success value or the provided default if this is an [Outcome.Error].
 *
 * @param value The default value to return if this is an [Outcome.Error].
 * @return The success value or the provided default.
 */
fun <E, S> Outcome<E, S>.successOr(value: S): S =
    when (this) {
        is Outcome.Error -> value
        is Outcome.Success -> data
    }

/**
 * Returns the error value or the provided default if this is a [Outcome.Success].
 *
 * @param value The default value to return if this is a [Outcome.Success].
 * @return The error value or the provided default.
 */
fun <E, S> Outcome<E, S>.errorOr(value: S): S =
    when (this) {
        is Outcome.Error -> value
        is Outcome.Success -> data
    }


/**
 * Returns the success value or null if this is an [Outcome.Error].
 *
 * @return The success value or null.
 */
fun <E, S> Outcome<E, S>.successOrNull(): S? =
    when (this) {
        is Outcome.Error -> null
        is Outcome.Success -> data
    }

/**
 * Returns the error value or null if this is a [Outcome.Success].
 *
 * @return The error value or null.
 */
fun <E, S> Outcome<E, S>.errorOrNull(): E? =
    when (this) {
        is Outcome.Error -> error
        is Outcome.Success -> null
    }

/**
 * Performs the given action if this is an [Outcome.Error].
 *
 * @param fn The action to perform on the error value.
 * @return The original [Outcome].
 */
fun <E, S> Outcome<E, S>.onError(fn: (error: E) -> Unit): Outcome<E, S> =
    this.apply { if (this is Outcome.Error) fn(error) }

/**
 * Executes the given suspend function if the [Outcome] is an [Outcome.Error].
 *
 * @param fn The suspend function to execute with the error value.
 * @return The original [Outcome] for chaining.
 */
suspend fun <E, S> Outcome<E, S>.onErrorSuspend(fn: suspend (error: E) -> Unit): Outcome<E, S> {
    if (this is Outcome.Error) {
        fn(error)
    }
    return this
}

/**
 * Performs the given action if this is a [Outcome.Success].
 *
 * @param fn The action to perform on the success value.
 * @return The original [Outcome].
 */
fun <E, S> Outcome<E, S>.onSuccess(transform: (success: S) -> Unit): Outcome<E, S> =
    this.apply { if (this is Outcome.Success) transform(data) }

/**
 * Executes the given suspend function if the [Outcome] is a [Success].
 *
 * @param fn The suspend function to execute with the success data.
 * @return The original [Outcome] for chaining.
 */
suspend fun <E, S> Outcome<E, S>.onSuccessSuspend(transform: suspend (S) -> Unit): Outcome<E, S> {
    if (this is Outcome.Success) {
        transform(data)
    }
    return this
}

/**
 * Extension function to flatMap a [Outcome] with a suspend function.
 *
 * @param fn The suspend function to apply if the result is [Success].
 * @return A new [Outcome] after applying the function.
 */
suspend fun <E, S, T> Outcome<E, S>.flatMapSuspend(transform: suspend (S) -> Outcome<E, T>): Outcome<E, T> {
    return when (this) {
        is Outcome.Error -> this
        is Outcome.Success -> transform(this.data)
    }
}

/**
 * Transforms the error value of an [Outcome] using a mapping function.
 *
 * If the result is an [Outcome.Success], it is returned unchanged.
 *
 * @param transform The mapping function to apply to the error value.
 * @return A new [Outcome] with the transformed error value.
 */
fun <E, F, V> Outcome<E, V>.flatMapError(transform: (E) -> Outcome<F, V>): Outcome<F, V> {
    return when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> transform(this.error)
    }
}

suspend fun <E, F, V> Outcome<E, V>.flatMapErrorSuspend(transform: suspend (E) -> Outcome<F, V>): Outcome<F, V> {
    return when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> transform(this.error)
    }
}


/**
 * Converts a [Outcome] into an `EmptyResult` by ignoring its success data.
 *
 * @return An `EmptyResult` containing the error value.
 */
fun <T, E> Outcome<E, T>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}


