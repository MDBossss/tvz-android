//package android.tvz.hr.listadobrinic.api
//
//import android.tvz.hr.listadobrinic.model.Car
//import android.util.Log
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//object CarApiHelper {
//    private val carApi = RetrofitHelper.carApi
//
//    fun getAllCars(callback: (List<Car>) -> Unit) {
//        val call = carApi.getCars()
//        call.enqueue(object : Callback<MutableList<Car>> {
//            override fun onResponse(call: Call<MutableList<Car>>, response: Response<MutableList<Car>>) {
//                Log.d("API Response", "RESPONSE")
//                if (response.isSuccessful) {
//                    val cars = response.body()
//                    Log.d("API Response", "Response: $cars")
//                    callback(cars!!)
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<Car>>, t: Throwable) {
//                // Handle error
//            }
//        })
//    }
//
//    fun createCar(car: Car, callback: (Car) -> Unit) {
//        val call = carApi.createCar(car)
//        call.enqueue(object : Callback<Car> {
//            override fun onResponse(call: Call<Car>, response: Response<Car>) {
//                if (response.isSuccessful) {
//                    val createdCar = response.body()
//                    callback(createdCar!!)
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<Car>, t: Throwable) {
//                // Handle error
//            }
//        })
//    }
//
//    fun updateCar(car: Car, callback: (Car) -> Unit) {
//        val call = carApi.updateCar(car.id, car)
//        call.enqueue(object : Callback<Car> {
//            override fun onResponse(call: Call<Car>, response: Response<Car>) {
//                if (response.isSuccessful) {
//                    val updatedCar = response.body()
//                    callback(updatedCar!!)
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<Car>, t: Throwable) {
//                // Handle error
//            }
//        })
//    }
//
//    fun deleteCar(id: Int, callback: () -> Unit) {
//        val call = carApi.deleteCar(id)
//        call.enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                if (response.isSuccessful) {
//                    callback()
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                // Handle error
//            }
//        })
//    }
//}