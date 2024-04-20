package android.tvz.hr.listadobrinic

import android.content.IntentFilter
import android.os.Bundle
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
            Car("Mercedes", "W210", "Silver", "https://i.pinimg.com/736x/9e/5f/37/9e5f37048fe10a4856e68516c4d895f3.jpg")
            )
    }

}
