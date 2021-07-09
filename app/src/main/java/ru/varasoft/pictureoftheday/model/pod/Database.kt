package ru.varasoft.pictureoftheday.model.pod

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [RoomPODServerResponseData::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val podDao: PODDao

    companion object {
        internal const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java, DB_NAME)
                    .build()
            }
        }
    }
}