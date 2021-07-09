package ru.varasoft.pictureoftheday.model.pod

import androidx.room.*

@Dao
interface PODDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pod: RoomPODServerResponseData)

    @Update
    fun update(pod: RoomPODServerResponseData)

    @Delete
    fun delete(pod: RoomPODServerResponseData)

    @Query("SELECT * FROM RoomPODServerResponseData WHERE date = :date LIMIT 1")
    fun findByDate(date: String): RoomPODServerResponseData?
}