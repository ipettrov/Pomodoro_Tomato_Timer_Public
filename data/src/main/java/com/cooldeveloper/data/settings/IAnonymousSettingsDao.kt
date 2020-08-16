package com.cooldeveloper.data.settings

import androidx.room.*
import com.cooldeveloper.data.datamodel.AnonymousRoomSettings

@Dao
interface IAnonymousSettingsDao {

    // Get settings by id
    @Query("SELECT * FROM anonymous_settings WHERE id = :id")
    fun getSettingsById(id: Int): AnonymousRoomSettings

    @Delete
    fun deleteSettings(settingsAnonymous: AnonymousRoomSettings)

    @Query("DELETE FROM anonymous_settings")
    fun deleteAll()

    //if update successful, will return number of rows effected, which should be 1
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateSettings(settingsAnonymous: AnonymousRoomSettings): Long
}