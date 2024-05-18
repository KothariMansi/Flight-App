package com.example.flightapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
class AirportData (
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    val passengers: Int,
)

@Entity(tableName = "favourite")
class FavouriteData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "departure_code")
    val departureCode: String = "",
    @ColumnInfo(name = "destination_code")
    val destinationCode: String = "",
)