package com.example.flightapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AirportData::class, FavouriteData::class], version = 1)
abstract class FlightDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        var Instance: FlightDatabase ?= null

        fun getDatabase(context: Context): FlightDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    FlightDatabase::class.java,
                    "flightDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}