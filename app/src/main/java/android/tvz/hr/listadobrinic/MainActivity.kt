package android.tvz.hr.listadobrinic

import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.tvz.hr.listadobrinic.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CarAdapter
    private var cars = ArrayList<Car>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceivers()

        cars = getCars()

        setupRecyclerView()

        binding.themeChange.setOnClickListener{
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            startActivity(intent)
        }

        if(this.resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme)
        }
        else{
            setTheme(R.style.LightTheme)
        }
    }

    // registering the battery low receiver
    private fun registerReceivers(){
        registerReceiver(BatteryLowReceiver(), IntentFilter("android.intent.action.BATTERY_LOW"))
    }

    private fun setupRecyclerView(){
        linearLayoutManager = LinearLayoutManager(this)
        adapter = CarAdapter(this,cars)

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun getCars(): ArrayList<Car>{
        // mock data
        return arrayListOf<Car>(
            Car("Mercedes","W124","Green","https://i.pinimg.com/736x/85/5f/38/855f38f134cb18e421c013b7420984a7.jpg"),
            Car("Mercedes", "W210", "Silver", "https://i.pinimg.com/736x/9e/5f/37/9e5f37048fe10a4856e68516c4d895f3.jpg"),
            Car("Toyota", "Corolla", "Red", "https://m1.secondhandapp.at/2.0/59b272586df44f0d9cace632?height=128&width=128"),
            Car("Honda", "Civic", "Blue", "https://i.imgur.com/qB87aOG.jpg"),
            Car("Ford", "Mustang", "Black", "https://i.imgur.com/PTjK26L.jpg"),
            Car("Chevrolet", "Camaro", "Yellow", "https://i.imgur.com/iO4FvZu.jpg"),
            Car("Nissan", "GT-R", "Silver", "https://i.imgur.com/vJbCkEN.jpg"),
            Car("Audi", "R8", "White", "https://i.imgur.com/vnqDfXK.jpg"),
            Car("BMW", "M3", "Blue", "https://i.imgur.com/NVjhZtG.jpg"),
            Car("Volkswagen", "Golf", "Red", "https://i.imgur.com/NNNIuYN.jpg"),
            Car("Kia", "Optima", "Black", "https://i.imgur.com/u4I2Ayr.jpg"),
            Car("Tesla", "Model S", "White", "https://i.imgur.com/XVsoM2L.jpg"),
            Car("Ferrari", "488 GTB", "Red", "https://i.imgur.com/yF2KSLb.jpg"),
            Car("Lamborghini", "Aventador", "Yellow", "https://i.imgur.com/7uH2cnH.jpg"),
            Car("Porsche", "911", "Black", "https://i.imgur.com/2PNzONs.jpg"),
            Car("Dodge", "Challenger", "Orange", "https://i.imgur.com/YnnT4s7.jpg"),
            Car("Jeep", "Wrangler", "Green", "https://i.imgur.com/u3ouY9N.jpg"),
            Car("Land Rover", "Defender", "Silver", "https://i.imgur.com/7yN2K0M.jpg"),
            Car("Hyundai", "Sonata", "Gray", "https://i.imgur.com/yo8ONjU.jpg"),
            Car("Volvo", "XC90", "White", "https://i.imgur.com/yZ49CNh.jpg"),
            )
    }

}
