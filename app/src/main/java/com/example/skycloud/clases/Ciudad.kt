package com.example.skycloud.clases

class Ciudad(name:String, weather:ArrayList<Weather>, main: Main, wind: Wind) {

    var name=""
    var weather:ArrayList<Weather>?=null
    var main: Main? =null
    var wind: Wind?=null


    init {

        this.name=name
        this.weather=weather
        this.main=main
        this.wind=wind

    }



}