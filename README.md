<p align="center"><img src="./app/src/main/ic_launcher-web.png?raw=true" width="200px"/></p>

<h1 align="center">Wafelbak Android application</h1>

The 'Wafelbak' Android application is an application made to facilitate the process of ordering waffles for the benefit of ([Scouts Wondelgem](http://www.scoutswondelgem.be)).

Users can log in and add there orders from anywhere, they don't have to worry that they'll have to wait till next year for those delicious waffles because they weren't home.
Admins can see all orders and search specific orders, and mark them complete or not.

> Admins will also be able to add orders for other users but that isn't implemented yet.

This project is part of the [Native Apps I](https://bamaflexweb.hogent.be/BMFUIDetailxOLOD.aspx?a=113418&b=1&c=1) course for the Bachelor of Applied Informatics at the Ghent University College [HoGent](https://www.hogent.be/en/) (Academic year 2019-2020).

<!-- TODO - Add screenshots
## Screenshots

-->

---

## Getting Started

<!-- TODO - Change href
You can't download the application on the Google Play store.
-->

### Installation

1. Clone this repo

    ```bash
    git clone https://github.com/HoGentTIN/native-apps-groep-d-VictorOwnt
    ```

2. Open the project in Android Studio

    ```bash
    studio native-apps-groep-d-VictorOwnt
    ```

3. Run the project on an emulator or physical device

#### Dummy login

Use the login credentials stated below to test the project's functionality.

Client:

- Email: *`client@mail.com`*
- Password: *`test00##`*

Supervisor:

- Email: *`admin@mail.com`*
- Password: *`test00##`*

### Generating signed APK

From Android Studio:

1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)* OPTIONAL

### REST backend

This application relies on a REST backend server.

1. Open the `Constants.kt` file located in the `util` package.
2. Change the value for `BASE_URL` to your own link.
3. Create a new Firebase project and change the `google-services.json` file accordingly.

The sourcecode for this server is located [here](https://github.com/VictorOwnt/WafelbakBackend). The server is hosted by [Heroku](https://www.heroku.com/) [![Heroku Backend Status](http://heroku-shields.herokuapp.com/wafelbak-backend)](https://wafelbak-backend.herokuapp.com).

## Built With

* [Retrofit](https://square.github.io/retrofit/)
* 
* [ReactiveX](http://reactivex.io/)

## Creator

| <a href="https://github.com/VictorOwnt" target="_blank">**Victor Van Hulle**</a> |
| --- |
| [![Victor](https://avatars2.githubusercontent.com/u/17174095?s=200)](https://github.com/VictorOwnt) |
