package android.tvz.hr.listadobrinic

import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.tvz.hr.listadobrinic.databinding.ActivityMainBinding
import android.tvz.hr.listadobrinic.model.Car
import android.tvz.hr.listadobrinic.model.CarDao
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CarAdapter
    private var cars = ArrayList<Car>()

    lateinit var carDao: CarDao
    lateinit var db: CarDatabase

    var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceivers()

        db = CarDatabaseHelper.getInstance(this)

        carDao = db.carDao()

//        carDao.insertCar(Car(1,"Mercedes","W124","Green","https://i.pinimg.com/736x/85/5f/38/855f38f134cb18e421c013b7420984a7.jpg"))
//        carDao.insertCar(Car(2,"Mercedes", "W210", "Silver", "https://i.pinimg.com/736x/9e/5f/37/9e5f37048fe10a4856e68516c4d895f3.jpg"))
//        carDao.insertCar(Car(3,"Toyota", "Corolla", "Red", "https://i.insider.com/5f5f966f7ed0ee001e25f20e?width=700"))

        cars = carDao.getCars() as ArrayList<Car>


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

        setupRecyclerView()


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
