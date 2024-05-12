package android.tvz.hr.listadobrinic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.tvz.hr.listadobrinic.R
import android.widget.ImageView
import androidx.fragment.app.ListFragment
import com.squareup.picasso.Picasso

class CarDetailFragment: ListFragment() {

    val ARG_ITEM_ID = "item_id"

    var mItem: Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments?.containsKey(ARG_ITEM_ID) == true) {
            mItem = requireArguments().getString(ARG_ITEM_ID)
                ?.let { getCars().getOrNull(it.toInt()) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_car_detail, container, false)
        // if such item exists, fill the values to the view inside that fragment
        if (mItem != null) {
            (rootView.findViewById<View>(R.id.carBrand) as TextView).text = mItem!!.brand
            (rootView.findViewById<View>(R.id.carModel) as TextView).text = mItem!!.model
            (rootView.findViewById<View>(R.id.carColor) as TextView).text = mItem!!.color

            Picasso.get().load(mItem!!.imageUrl).into((rootView.findViewById<View>(R.id.carImage) as ImageView))

            (rootView.findViewById<View>(R.id.carImage) as ImageView).setOnClickListener{v: View? -> v?.let {
                val context = it.context
                val showImageIntent = Intent(context, ImageActivity::class.java)
                showImageIntent.putExtra("CAR_DETAILS",mItem)
                context.startActivity(showImageIntent)
            }}
        }

        return rootView
    }

    private fun getCars(): ArrayList<Car>{
        // mock data
        return arrayListOf<Car>(
            Car("1","Mercedes","W124","Green","https://i.pinimg.com/736x/85/5f/38/855f38f134cb18e421c013b7420984a7.jpg"),
            Car("2","Mercedes", "W210", "Silver", "https://i.pinimg.com/736x/9e/5f/37/9e5f37048fe10a4856e68516c4d895f3.jpg"),
            Car("3","Toyota", "Corolla", "Red", "https://m1.secondhandapp.at/2.0/59b272586df44f0d9cace632?height=128&width=128"),
            Car("4","Honda", "Civic", "Blue", "https://i.imgur.com/qB87aOG.jpg"),
            Car("5","Ford", "Mustang", "Black", "https://i.imgur.com/PTjK26L.jpg"),
            Car("6","Chevrolet", "Camaro", "Yellow", "https://i.imgur.com/iO4FvZu.jpg"),
            Car("7","Nissan", "GT-R", "Silver", "https://i.imgur.com/vJbCkEN.jpg"),
            Car("8","Audi", "R8", "White", "https://i.imgur.com/vnqDfXK.jpg"),
            Car("9","BMW", "M3", "Blue", "https://i.imgur.com/NVjhZtG.jpg"),
            Car("10","Volkswagen", "Golf", "Red", "https://i.imgur.com/NNNIuYN.jpg"),
            Car("11","Kia", "Optima", "Black", "https://i.imgur.com/u4I2Ayr.jpg"),
            Car("12","Tesla", "Model S", "White", "https://i.imgur.com/XVsoM2L.jpg"),
            Car("13","Ferrari", "488 GTB", "Red", "https://i.imgur.com/yF2KSLb.jpg"),
            Car("14","Lamborghini", "Aventador", "Yellow", "https://i.imgur.com/7uH2cnH.jpg"),
            Car("15","Porsche", "911", "Black", "https://i.imgur.com/2PNzONs.jpg"),
            Car("16","Dodge", "Challenger", "Orange", "https://i.imgur.com/YnnT4s7.jpg"),
            Car("17","Jeep", "Wrangler", "Green", "https://i.imgur.com/u3ouY9N.jpg"),
            Car("18","Land Rover", "Defender", "Silver", "https://i.imgur.com/7yN2K0M.jpg"),
            Car("19","Hyundai", "Sonata", "Gray", "https://i.imgur.com/yo8ONjU.jpg"),
            Car("20","Volvo", "XC90", "White", "https://i.imgur.com/yZ49CNh.jpg"),
        )
    }
}