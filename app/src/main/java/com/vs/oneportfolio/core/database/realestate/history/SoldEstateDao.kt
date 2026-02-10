package com.vs.oneportfolio.core.database.realestate.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SoldEstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoldEstate(soldEstate: SoldEstateEntity)

    @Query("SELECT * FROM soldestate")
    fun getAllSoldEstates(): Flow<List<SoldEstateEntity>>

}