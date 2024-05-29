package android.tvz.hr.listadobrinic.local

import android.tvz.hr.listadobrinic.model.Car
import android.tvz.hr.listadobrinic.local.CarDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase : RoomDatabase(){
    abstract fun carDao(): CarDao
}