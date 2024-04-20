package android.tvz.hr.listadobrinic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val context: Context, private val cars: ArrayList<Car>) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bindCar(car)
    }

    override fun getItemCount(): Int = cars.size

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val carBrand: TextView = itemView.findViewById(R.id.car_brand)
        private val carModel: TextView = itemView.findViewById(R.id.car_model)
        private val carColor: TextView = itemView.findViewById(R.id.car_color)


        fun bindCar(car: Car) {
            carBrand.text = car.brand
            carModel.text = car.model
            carColor.text = car.color

            itemView.setOnClickListener {
                // Implement navigation to a details activity or other action
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("CAR_DETAILS", car) // Key for sending car object
                context.startActivity(intent)
            }
        }
    }
}
