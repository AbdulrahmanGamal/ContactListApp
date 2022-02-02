# ContactListApp
 is an android app shows list of users using the https://randomuser.me/  open-source API for generating random user data.
 
this app  implements clean architecture and SOLID principles to make code more maintainable and testable and readable

app layers
- Data layer : which contains webservices , repositories and room as local database and saving loaded pages 
- Domain layer : which contains usecases used in the app
- Presentation layer : contains the app views and view-models

# Technologies used
This app build with kotlin
- **Retrofit and coroutines**
   >to make asynchronous  http requests with power of coroutines suspended methods as it's easy to implement thrading and asynchronous jobs in android with code written sequentially  so the code is more readable and easy to maintain with coroutines

-  **KOIN dependency injection**
   > to provide instances our classes, with the power of kotlin i used  [Koin](https://insert-koin.io)  as a dependency injection library because it's  lightweight dependency injection framework for Kotlin Written in pure Kotlin using functional resolution only:  no code generation!

-  **Android Architecture**
   > using ( Flow as a stream and view-model) to implement MVVM Pattern to design robust, testable, and maintainable app with more power over lifecycle management and data persistence.
   > Pagination is using [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) library 
-  **Unit testing**
   > mockito as for mocking and unit test




