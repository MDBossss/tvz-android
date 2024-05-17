package android.tvz.hr.listadobrinic

import android.content.Intent
import android.tvz.hr.listadobrinic.model.Car
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FCM Token"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val db = CarDatabaseHelper.getInstance(applicationContext)
        val carDao = db.carDao()
        val car = carDao.getCarById(1)

        val intent = Intent(applicationContext, ImageActivity::class.java)
        intent.putExtra("CAR_DETAILS",car)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Log.d(TAG,"From: " + message.from!!)
        Log.d(TAG, "Notifiation Message Body: " + message.notification!!.body!!)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }
}