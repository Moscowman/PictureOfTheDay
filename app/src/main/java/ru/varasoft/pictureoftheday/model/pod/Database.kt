package ru.varasoft.pictureoftheday.model.pod

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [RoomPODServerResponseData::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val podDao: PODDao

    companion object {
        internal const val DB_NAME = "database.db"
    }
}