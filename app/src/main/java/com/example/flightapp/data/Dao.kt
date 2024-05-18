package com.example.flightapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM airport WHERE name like'%' || :text || '%'")
    fun getFlightByHint(text: String): Flow<List<AirportData>>

    @Query("SELECT * FROM airport WHERE iata_code=:code")
    fun getFlightByCode(code: String): Flow<List<AirportData>>

    @Insert
    suspend fun insertFavourite(favouriteData: FavouriteData)

    @Query("SELECT * FROM favourite")
    fun getFavouriteList(): Flow<List<FavouriteData>>

}