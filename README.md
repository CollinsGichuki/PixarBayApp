<p align="center">
  <h2>PixarBay Application</h2>

  <p>
  An Android app consuming the Pixabay API to display Images. It is built using the Repository and MVVM patterns as well as Architecture Components.
  
  Min Api Level : 21 Supports over 87% devices
  
  Build System : Gradle
    </p>
</p>

<br>


## Table of contents
- [Prerequisite](#Prerequisite)
- [Testing](#Testing)
- [Libraries](#Libraries)

<hr>

### Prerequisite

After cloning the repo, visit [Pixabay](https://pixabay.com/api/docs) and get an api key.
Create a file apikey.properties and save the api key in it like so.

  `API_KEY="#########################"`

<hr>

### Testing
Work In Progresss 🚧

<hr>

### Libraries

- [Jetpack🚀](https://developer.android.com/jetpack)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way and act as a channel between the repository and the ui
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - support library that allows binding of UI views by providing a binding class that contains direct references to all views that have an ID in the corresponding layout.
    - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - a life-cycle aware observable data holder class

- [Coroutines](https://developer.android.com/kotlin/coroutines) - a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- [Flow](https://developer.android.com/kotlin/flow) - a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back.
- [ok-http-logging-interceptor](https://square.github.io/okhttp/interceptors/) - intercepts the request and adds the api key
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Lightweight dependency injection library for android.

Twitter:  [@_snillock](https://twitter.com/_snillock)

