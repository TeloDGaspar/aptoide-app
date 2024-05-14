package com.aptoide_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FullDetailAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(
        fullDetailAppEntity: FullDetailAppEntity
    )

    @Query("Delete From FullDetailAppEntity")
    suspend fun clearCompanyListings()

    @Query("SELECT * FROM FullDetailAppEntity")
    suspend fun getFullDetailApp(): List<FullDetailAppEntity>
}