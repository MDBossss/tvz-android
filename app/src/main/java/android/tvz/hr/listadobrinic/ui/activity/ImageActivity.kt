package android.tvz.hr.listadobrinic.ui.activity

import android.net.Uri
import android.os.Bundle
import android.tvz.hr.listadobrinic.R
import android.tvz.hr.listadobrinic.databinding.ActivityImageBinding
import android.tvz.hr.listadobrinic.model.Car
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.drawee.view.SimpleDraweeView

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val item = intent.getParcelableExtra<Car>("CAR_DETAILS")
        if(item != null){
//            Picasso.get().load(item.imageUrl).into(binding.image)
            val uri = Uri.parse(item.imageUrl)
            val draweeView = binding.image as SimpleDraweeView
            draweeView.setImageURI(uri)
        }
    }
}