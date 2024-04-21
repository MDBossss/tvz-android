package android.tvz.hr.listadobrinic

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.tvz.hr.listadobrinic.databinding.ActivityDetailsBinding
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var car: Car? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        car = intent.getParcelableExtra<Car>("CAR_DETAILS").also{
            if (it != null) {
                bindCar(it)
            }
        }

        val blinkAnim = ObjectAnimator.ofFloat(binding.carBrand, "alpha", 0.0f, 1.0f) // Fade from 0 (invisible) to 1 (visible)
        blinkAnim.duration = 500 // Set animation duration in milliseconds
        blinkAnim.repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        blinkAnim.repeatMode = ValueAnimator.REVERSE // Reverse the animation for blinking effect
        blinkAnim.start() // Start the animation

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            actionShare()
        } else if (item.itemId == R.id.action_open_url) {
            actionOpenUrl()
        }

        return true
    }

    private fun actionShare() {
        car?.imageUrl?.let { imageUrl ->
            val confirmationDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.share_car_title))
                .setMessage(getString(R.string.share_car_message))
                .setPositiveButton(getString(R.string.action_share)) { _, _ ->
                    // Send broadcast with image URL
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, imageUrl)
                    sendBroadcast(intent)

                    // Show toast message for successful share
                    Toast.makeText(this, getString(R.string.share_car_success), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.action_cancel)) { _, _ ->
                    // User cancels sharing
                }
                .create()
            confirmationDialog.show()
        }
    }


    private fun actionOpenUrl() {
        car?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.imageUrl)
            startActivity(intent)
        }
    }

    private fun bindCar(car:Car){
        binding.carBrand.text = car.brand
        binding.carModel.text = car.model;
        binding.carColor.text  =car.color

        Picasso.get().load(car.imageUrl).into(binding.carImage)

        binding.carImage.setOnClickListener{v: View? -> v?.let {
            val context = it.context
            val showImageIntent = Intent(context, ImageActivity::class.java)
            showImageIntent.putExtra("CAR_DETAILS",car)
            context.startActivity(showImageIntent)
        }}
    }
}