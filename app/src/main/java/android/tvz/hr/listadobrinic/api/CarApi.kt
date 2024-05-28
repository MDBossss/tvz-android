package android.tvz.hr.listadobrinic.api

import android.tvz.hr.listadobrinic.model.Car
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CarApi {
    @GET("/api/cars")
    fun getCars(): Call<MutableList<Car>>

    @GET("/api/cars/latest")
    fun getLatestCar(): Call<Car>

    @POST("/api/cars")
    fun createCar(@Body car: Car): Call<Car>

    @PUT("/api/cars/{id}")
    fun updateCar(@Path("id") id: Int, @Body car: Car): Call<Car>

    @DELETE("/api/cars/{id}")
    fun deleteCar(@Path("id") id: Int): Call<Unit>
}