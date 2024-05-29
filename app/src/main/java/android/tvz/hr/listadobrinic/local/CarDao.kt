package android.tvz.hr.listadobrinic.local

import android.tvz.hr.listadobrinic.model.Car
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarDao {
    @Query("SELECT * FROM car")
    fun getCars(): List<Car>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getCarById(id: Int): Car

    @Insert
    fun insertCar(car: Car)

    @Delete
    fun deleteCar(car: Car)
}