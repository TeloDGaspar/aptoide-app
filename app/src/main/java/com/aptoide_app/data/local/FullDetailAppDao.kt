package com.aptoide_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the FullDetailApp table.
 * This is an interface that is implemented by Room.
 * It provides methods to perform database operations on the FullDetailApp table.
 */
@Dao
interface FullDetailAppDao {

    /**
     * Inserts a FullDetailAppEntity into the FullDetailApp table.
     * If a FullDetailAppEntity with the same ID already exists in the table, it is replaced.
     *
     * @param fullDetailAppEntity The FullDetailAppEntity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(
        fullDetailAppEntity: FullDetailAppEntity
    )
    /**
     * Deletes all FullDetailAppEntity from the FullDetailApp table.
     */
    @Query("Delete From FullDetailAppEntity")
    suspend fun clearCompanyListings()
    /**
     * Fetches all FullDetailAppEntity from the FullDetailApp table.
     *
     * @return A list of FullDetailAppEntity.
     */
    @Query("SELECT * FROM FullDetailAppEntity")
    suspend fun getFullDetailApp(): List<FullDetailAppEntity>
}