package com.vs.oneportfolio.core.database.crypto.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SoldCryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoldCrypto(soldCrypto: SoldCrypto)

    @Query("SELECT * FROM soldCrypto")
    fun getAllSoldCrypto(): Flow<List<SoldCrypto>>


}