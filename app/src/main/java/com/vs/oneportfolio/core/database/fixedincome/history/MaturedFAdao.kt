package com.vs.oneportfolio.core.database.fixedincome.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MaturedFADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaturedFA(maturedFA: MaturedFEntity)

    @Query("SELECT * FROM matured_fixed_deposit")
    fun getAllMaturedFA(): Flow<List<MaturedFEntity>>

}