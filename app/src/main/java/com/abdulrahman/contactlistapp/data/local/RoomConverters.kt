package com.abdulrahman.contactlistapp.data.local

import androidx.room.TypeConverter
import com.abdulrahman.contactlistapp.data.entities.Location
import com.abdulrahman.contactlistapp.data.entities.Picture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RoomConverters {

    @TypeConverter
    fun getLocation(value: String?): Location? {
        val listType = object : TypeToken<Location?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        val gson = Gson()
        return gson.toJson(location)

    }

    @TypeConverter
    fun getPicture(value: String?): Picture? {
        val listType = object : TypeToken<Picture?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPicture(picture: Picture?): String? {
        val gson = Gson()
        return gson.toJson(picture)
    }

}