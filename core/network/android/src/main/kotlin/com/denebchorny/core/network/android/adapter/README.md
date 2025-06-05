## BEFORE ANYTHING
The files in this directory belong to the "NetworkResponse Retrofit adapter" library by "haroldadmin" on Github.

These files have been copied directly to this project, and the library has not been imported into it due to the following:

1. The library does not have a high update/maintenance rate.

2. The library in its latest version depends on kotlin 1.6, while the current project depends on kotlin 2.+.

3. The dependencies of retrofit and okhttp could vary from our project if we maintain them at a higher rate than the library.

Having said this, this allows us to adapt the code to the current dependencies of retrofit and okhttp, keeping its maintenance in our hands.

No problems, no worries

# NetworkResponse Retrofit adapter
This library provides a Kotlin Coroutines based Retrofit call adapter for wrapping your API responses in  
a `NetworkResponse` sealed type.

## Documentation

[**https://haroldadmin.github.io/NetworkResponseAdapter**](https://haroldadmin.github.io/NetworkResponseAdapter)

## Network Response

`NetworkResponse<S, E>` is a Kotlin sealed interface with the following states:

- `Success`: Represents successful network calls (2xx response codes)
- `Error`: Represents unsuccessful network calls
  - `ServerError`: Server errors (non 2xx responses)
  - `NetworkError`: IO Errors, connectivity problems
  - `UnknownError`: Any other errors, like serialization exceptions

It is generic on two types: a success response (`S`), and an error response (`E`).

- `S`: Kotlin representation of a successful API response
- `E`: Representation of an unsuccessful API response

Find complete documentation at [**https://haroldadmin.github.io/NetworkResponseAdapter**](https://haroldadmin.github.io/NetworkResponseAdapter).