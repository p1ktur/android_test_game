# Rocket Game
Description: In this game you endlessly fly a rocket where sometimes you are able to collect either score or gems with which you may unlock new skins in the shop. Also in the settings you may view your records and set game difficulty. The result screen shows the result of your run where you can see amount of score collected during last run and your overall records. In the shop there are 4 categories of skins available to be unlocked and then used.

In this project I used such libraries as Jetpack Compose, Coroutines, Flows, Room, Coin for downloading images, SharedPref, Dagger-Hilt for Dependency Injection. Additionally I used Activity and ViewModels. Whole project is structured and divided in 3 layers - data, domain and presenter.

---IMPORTANT NOTES---
I also planned to use Retrofit in my project, but didn't find any free API which could be somehow applied to my game. Still I could write my own JS Server which could process my HTTP requests, but since it would run only on localhost I decided not to.
Also SplashScreen is implemented not via splash screen api because in requirements to this task custom progressbar is required which I didn't find how to implement without using separate activity.
Also I tried following a Clean Architecture approach but did not implement use cases.
