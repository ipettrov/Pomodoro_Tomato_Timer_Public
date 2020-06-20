package com.cooldeveloper.data.settings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cooldeveloper.data.datamodel.AnonymousRoomSettings

private const val DATABASE_ANON = "anonymous"

@Database(
    entities = [AnonymousRoomSettings::class],
    version = 4,
    exportSchema = false
)
abstract class AnonymousSettingsDatabase : RoomDatabase() {

    abstract fun roomSettingsDao(): IAnonymousSettingsDao

    //code below courtesy of https://github.com/googlesamples/android-sunflower; it is open source
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AnonymousSettingsDatabase? = null

        fun getInstance(context: Context): AnonymousSettingsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AnonymousSettingsDatabase {
            return Room.databaseBuilder(
                context,
                AnonymousSettingsDatabase::class.java,
                DATABASE_ANON
            ).build()
        }
    }
}