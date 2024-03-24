package hr.tvz.android.kalkuatordobrinic

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.tvz.android.kalkuatordobrinic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val colorChangeListener = object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val color = calculateColor(progress)
            changeAllTextColors(color)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.isHpToKwInput.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.inputLabel.text = getString(R.string.enterHp)
            } else {
                binding.inputLabel.text = getString(R.string.enterKw)
            }
        }

        binding.colorSlider.setOnSeekBarChangeListener(colorChangeListener)
    }

    fun handleCalculate(view: View){
        val inputValue = binding.valueInput.text.toString().toDouble()
        val isHpToKw = binding.isHpToKwInput.isChecked

        if(isHpToKw){
           binding.result.text = String.format("%.2f KW", inputValue * 0.745699872)
        }
        else{
            binding.result.text = String.format("%.2f HP", inputValue * 1.34102209)
        }
    }

    private fun calculateColor(progress: Int): Int {
        val hue = progress.toFloat() / 100f * 360f
        return Color.HSVToColor(floatArrayOf(hue, 1f, 1f))
    }

    private fun changeAllTextColors(color: Int) {
        changeTextColorRecursive(binding.root, color)
    }

    private fun changeTextColorRecursive(view: View, color: Int) {
        if (view is TextView) {
            view.setTextColor(color)
        } else if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount) {
                changeTextColorRecursive((view as ViewGroup).getChildAt(i), color)
            }
        }
    }

}