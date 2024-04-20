package android.tvz.hr.listadobrinic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BatteryLowReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Battery low...", Toast.LENGTH_LONG).show()
//        context?.setTheme(R.style.LightTheme)
    }
}