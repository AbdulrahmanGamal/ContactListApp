package com.abdulrahman.contactlistapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdulrahman.contactlistapp.data.entities.Location
import com.abdulrahman.contactlistapp.data.entities.Picture
import com.google.gson.annotations.SerializedName

@TypeConverters(RoomConverters::class)
@Entity
data class UserDBEntity(
    @PrimaryKey
    val id: String,
    var gender: String,
    var name: String,
    var location: Location,
    var email: String,
    var dob: String,
    var registered: String,
    var phone: String,
    var picture: Picture,
)