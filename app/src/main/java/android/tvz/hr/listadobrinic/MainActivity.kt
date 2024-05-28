package android.tvz.hr.listadobrinic

import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.tvz.hr.listadobrinic.api.CarApi
import android.tvz.hr.listadobrinic.api.ServiceGenerator
import android.tvz.hr.listadobrinic.databinding.ActivityMainBinding
import android.tvz.hr.listadobrinic.model.Car
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CarAdapter
    private var cars = ArrayList<Car>()

    val BASE_URL = "http://10.0.2.2:3000"

    var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceivers()

        val client: CarApi = ServiceGenerator().createService(CarApi::class.java,BASE_URL)

        val carsCall: Call<MutableList<Car>> = client.getCars()

        carsCall.enqueue(object : Callback<MutableList<Car>>{
            override fun onResponse(
                call: Call<MutableList<Car>>,
                response: Response<MutableList<Car>>
            ) {
                if(response.isSuccessful){
                    cars = response.body() as ArrayList<Car>
                    setupRecyclerView()
                }
            }

            override fun onFailure(call: Call<MutableList<Car>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })

        binding.themeChange?.setOnClickListener{
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            startActivity(intent)
        }

        if(this.resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme)
        }
        else{
            setTheme(R.style.LightTheme)
        }

        // NEW LANDSCAPE STUFF

        // if there is no details view, then its not 2 sided
        if(findViewById<FrameLayout>(R.id.car_detail_container) != null){
            mTwoPane = true
        }


        // Firebase
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(!task.isSuccessful){
                Log.w("Main activity", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("Main activity token:", token)
            Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
        })



    }


    // registering the battery low receiver
    private fun registerReceivers(){
        registerReceiver(BatteryLowReceiver(), IntentFilter("android.intent.action.BATTERY_LOW"))
    }

    private fun setupRecyclerView(){
        linearLayoutManager = LinearLayoutManager(this)
        adapter = CarAdapter(this,this,cars, mTwoPane)

        binding.itemList.layoutManager = linearLayoutManager
        binding.itemList.adapter = adapter
    }
}
