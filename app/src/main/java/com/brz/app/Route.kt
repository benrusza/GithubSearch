package com.brz.app

sealed class Route(val route: String) {
    object MainScreen: Route ("MainScreen")
    object DetailScreen: Route ("DetailScreen/{user}")


    fun ofUser(user : String) : String{
        return DetailScreen.route.replace("{user}",user)
    }
}