package com.abdulrahman.contactlistapp.data.entities

import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("name") var name: String,
    @SerializedName("value") var value: String
)

data class Location(
    @SerializedName("city") var city: String,
    @SerializedName("state") var state: String,
    @SerializedName("postcode") var postcode: String,
    @SerializedName("coordinates") var coordinates: Coordinates,
)
data class Coordinates(
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
)

data class Login(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String,
    @SerializedName("uuid") var uuid: String,
    @SerializedName("salt") var salt: String,
    @SerializedName("md5") var md5: String,
    @SerializedName("sha1") var sha1: String,
    @SerializedName("sha256") var sha256: String
)

data class Name(
    @SerializedName("title") var title: String,
    @SerializedName("first") var first: String,
    @SerializedName("last") var last: String
) {
    fun fullName(): String = "$title $first $last"
}

data class Picture(
    @SerializedName("large") var large: String,
    @SerializedName("medium") var medium: String,
    @SerializedName("thumbnail") var thumbnail: String
)

data class UserDate(
    @SerializedName("date") var date: String,
    @SerializedName("age") var age: String,
)

data class User(
    @SerializedName("gender") var gender: String,
    @SerializedName("name") var name: Name,
    @SerializedName("location") var location: Location,
    @SerializedName("email") var email: String,
    @SerializedName("login") var login: Login,
    @SerializedName("dob") var dob: UserDate,
    @SerializedName("registered") var registered: UserDate,
    @SerializedName("phone") var phone: String,
    @SerializedName("cell") var cell: String,
    @SerializedName("id") var id: Id,
    @SerializedName("picture") var picture: Picture,
    @SerializedName("nat") var nat: String
)