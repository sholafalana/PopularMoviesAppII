# PopularMoviesAppII
Android Developer NanoDegree Project

Here is a fully functional and colorful android app which I made from scratch for the Android Developer Nanodegree program.

With the app, you can:

   * Discover the most popular, the most rated or the highest rated movies
   * Save favorite movies locally to view them even when offline
   * Watch trailers
   * Read reviews

# How to Work with the Source

This app uses The Movie Database API to retrieve movies. You must provide your own API key in order to build the app. When you get it, just paste it to: app/build.gradle

![20190506_121010](https://user-images.githubusercontent.com/32399318/57221955-d6555580-6ff8-11e9-9b7b-71bdab7e89f1.gif)

## Features

* MVVM with Android Architecture Components

* Present users with a grid arrangement of movie posters upon launch.

* Allow users to change sort order via a setting: The sort order can be by most popular or by highest-rated (movie data fetched from the Internet with the MovieDB API) or by favorite movies (movie data fetched from the local database).

* Implemented Room Database to save the favorite movies data.

* Material design.

* Allow users to tap on a movie poster and transition to a detail screen with additional information such as: original title / movie poster image thumbnail / a plot synopsis / user rating / release date / trailers / reviews

* Allow users to view and play trailers via an Intent either to launch in Youtube app or a web browser (if Youtube app isn't installed on the user's phone).

* Allow users to mark a movie as a favorite in the details view by tapping the star floating action button. Allow users to delete a movie from the favorite movie database by tapping the star button again.

* Unchecked on the star indicates the movie is not stored in the database. To display the details requires an API request. Checked on the star indicates the movie is stored in the database. To display the details does not require an API request.

* Implemented sharing functionality to allow the user to share the first trailer's Youtube URL from the movie details screen.


* Incorporated libraries to simplify the amount of code, such as: Using Picasso to fetch images and load them into views. Using RecyclerView  to implement efficient layout design.

* Used adapters and viewholders to populate list views. 

* Support accessibility for vision-limited users.

* Stored all the strings in string.xml. Stored all the dimens in dimens.xml.


## Prerequisites

* Android Studio IDE 3.0+
* Android SDK v27
* Android Build Tools v28.0.1
* Gradle 4.4

## Libraries

* Volley
* GSON
* ViewModel
* Room
* Picasso
* Material design

