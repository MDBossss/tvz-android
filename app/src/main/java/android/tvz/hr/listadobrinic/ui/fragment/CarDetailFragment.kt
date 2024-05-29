package android.tvz.hr.listadobrinic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.tvz.hr.listadobrinic.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.tvz.hr.listadobrinic.model.Car
import android.tvz.hr.listadobrinic.ui.activity.ImageActivity
import android.widget.ImageView
import com.squareup.picasso.Picasso

class CarDetailFragment: Fragment() {

    var mItem: Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            mItem = it.getParcelable("CAR_DETAILS")
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

            (rootView.findViewById<View>(R.id.carImage) as ImageView).setOnClickListener{ v: View? -> v?.let {
                val context = it.context
                val showImageIntent = Intent(context, ImageActivity::class.java)
                showImageIntent.putExtra("CAR_DETAILS",mItem)
                context.startActivity(showImageIntent)
            }}
        }

        return rootView
    }
}