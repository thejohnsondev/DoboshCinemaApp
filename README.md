# Cinema app
![Dark logo](https://user-images.githubusercontent.com/73844793/135581191-6aa79386-385a-4360-a28b-b978b64a97d7.png)
## Introduction
This is an Android application that allows you to discover the most popular, interesting and other movies and actors. The application is based on the MVVM architecture, and [The Movie Database API](https://developers.themoviedb.org/3/getting-started/introduction) is used to receive data.
> In this repository API key is not included

## Technologies
- **Android SDK, Kotlin** - framework and language
- **OkHTTP, Retrofit 2** - for network
- **Kotlin Coroutines** - for async work
- **Room, Shared preferences** - for local data storage
- **Glide, Coil** - for image loading

## Architecture
- **MVVM** - app architecture
- **Architecture components:** [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started), [ViewBinding](https://developer.android.com/topic/libraries/view-binding), [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager), [Room](https://developer.android.com/training/data-storage/room)

## Features
### - Home screen
The home screen displays lists of popular movies, top-rated movies, upcoming movies, popular genres, and actors:

![home_1](https://user-images.githubusercontent.com/73844793/135605491-4d34ffed-b99d-4425-9d8e-b89f2fde87f4.png)    ![home_2](https://user-images.githubusercontent.com/73844793/135605510-646a41ff-1c2c-4b81-8d62-4b93c04f97c1.png)

We can also watch movies by genre and movies by category:

![home_by_genre](https://user-images.githubusercontent.com/73844793/135605818-8422c745-26c5-4c38-afed-6db6002bf55e.png) ![home_popular](https://user-images.githubusercontent.com/73844793/135605980-69ca6d56-5912-46f6-94b1-0e326f4154d2.png)

### - Movie details screen
This screen includes detailed information about the movie, including audience rating, age limit, filmmakers, genres, countries of production, production companies, and other information that is shown below on the screen. Also this screen includes movie posters and backgrounds:

![movie_1](https://user-images.githubusercontent.com/73844793/135606881-94129b1a-d5b7-4e35-b96f-ca89839c1278.png) ![movie_2](https://user-images.githubusercontent.com/73844793/135606890-46dbb8ec-a04d-4b17-bf83-b90c594ff2b0.png) ![movie_backdrops](https://user-images.githubusercontent.com/73844793/135606936-d23f21ec-8af7-417b-8868-80e5ed929c7e.png)

Here you can see the movie cast, a list of recommendations for the movie, a list of similar movies and trailers for the movies:

![movie_actors](https://user-images.githubusercontent.com/73844793/135607436-cee18cea-f541-459b-99e9-4b6cfaa53068.png) ![movie_recomend](https://user-images.githubusercontent.com/73844793/135607455-bf4868b0-ddea-43af-8ca5-c0e3b8462485.png)![movie_trailers](https://user-images.githubusercontent.com/73844793/135607462-b405672f-d17f-44cd-970f-bd7ce67cf4bd.png)

### - Actor details screen
This screen contains detailed information about the actor, including age, place of birth, short biography, images, and other information that is shown below on the screen. Also here you can see the movies in which the actor was filmed:

![actor_1](https://user-images.githubusercontent.com/73844793/135608134-0e70f60f-e2c0-45c9-8eea-2fa3c5a3bc6d.png) ![actor_2](https://user-images.githubusercontent.com/73844793/135608152-e3838856-af15-490b-9331-04ab60711766.png) ![actor_movies](https://user-images.githubusercontent.com/73844793/135608169-69c1cb3f-8d09-433c-8a49-e90ec50f0188.png)

### - Search screen
Here you can search for movies and actors. Multi-search works here in real time, and allows you to find movies and actors at the same time by a word in the search bar:

![search_1](https://user-images.githubusercontent.com/73844793/135609139-d563e410-e25a-4b4a-8893-e7215fff3071.png)

### - Favorite movies and actors screen
Here you will find lists of favorite movies and actors. Movies and actors can be added to favorites from movie and actor detail screens:

![favorite_movies](https://user-images.githubusercontent.com/73844793/135608684-8edd9a12-08d7-4ab9-9fb2-aea69e528a85.png) ![favorite_actors](https://user-images.githubusercontent.com/73844793/135608690-9f6dd4f5-5586-475b-b8ab-4b191e6c7117.png)

### - Settings screen
Here you can configure several application settings, such as: interface settings (theme, image resolution), content settings (age categories, content language), change of application language, interval between notifications, user country, and information about app. This screen is currently in active development:

![settings_1](https://user-images.githubusercontent.com/73844793/135609876-2315b688-d459-455d-a8a7-9ce322cb4b97.png)

## Project setup
To build a project and run it, you need to follow these steps:
1. Sign up and retrieve a API key from [The Movie Database API](https://developers.themoviedb.org/3/getting-started/introduction)
2. Download and open the project in Android Studio
3. Edit the file **local.properties** and add the line: **apiKey="---YOUR API KEY---"**
4. Build and run the app on a physical device or emulator in Android studio
5. Mission completed :)
