package com.vs.oneportfolio.core.database.metals.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SoldMetalDao {
    @Insert
    suspend fun insertSoldMetal(soldMetal: SoldMetalEntity)

    @Query("SELECT * FROM soldmetal")
    fun  getAllSoldMetals () : Flow<List<SoldMetalEntity>>


}