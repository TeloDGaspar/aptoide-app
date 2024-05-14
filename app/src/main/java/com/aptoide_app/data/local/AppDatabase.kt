package com.aptoide_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import javax.inject.Inject

@Database(
    entities = [FullDetailAppEntity::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fullDetailAppDao(): FullDetailAppDao
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return value.joinToString(",")
    }
}
