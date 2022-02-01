package com.abdulrahman.contactlistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserDBEntity::class,
        RemoteKeys::class
    ],
    version = Db.VERSION,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class Db : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val NAME = "contacts-db"
        const val VERSION = 1
    }
}