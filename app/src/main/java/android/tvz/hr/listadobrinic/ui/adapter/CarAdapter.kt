package android.tvz.hr.listadobrinic.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.tvz.hr.listadobrinic.ui.fragment.CarDetailFragment
import android.tvz.hr.listadobrinic.ui.activity.DetailsActivity
import android.tvz.hr.listadobrinic.ui.activity.MainActivity
import android.tvz.hr.listadobrinic.R
import android.tvz.hr.listadobrinic.model.Car
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val parentActivity: MainActivity, private val context: Context, private val cars: ArrayList<Car>, private val twoPane: Boolean) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view, parentActivity, twoPane)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bindCar(car)
    }

    override fun getItemCount(): Int = cars.size

    inner class CarViewHolder(itemView: View, private val parent: Context, private val twoPane: Boolean) : RecyclerView.ViewHolder(itemView) {
        private val carItem: TextView = itemView.findViewById(R.id.car_item)

        fun bindCar(car: Car) {
            val carText = "${car.brand} ${car.model}, ${car.color}"
            carItem.text = carText

            itemView.setOnClickListener {

                if(twoPane){
                    val fragment = CarDetailFragment().apply {
                        arguments = Bundle().apply{
                            putParcelable("CAR_DETAILS",car)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.car_detail_container, fragment)
                        .commit()
                }else{
                    // Implement navigation to a details activity or other action
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("CAR_DETAILS", car) // Key for sending car object
                    context.startActivity(intent)
                }

            }
        }
    }
}
