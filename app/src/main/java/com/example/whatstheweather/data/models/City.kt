package com.example.whatstheweather.data.models

data class City (
	val id : Int,
	val name : String,
	val country : String,
	val coords: Coord,
	val population : Int,
	val timezone : Int,
	val sunrise : Int,
	val sunset : Int
)