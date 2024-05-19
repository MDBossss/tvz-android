package android.tvz.hr.listadobrinic

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.tvz.hr.listadobrinic.model.Car
import android.util.Log
import androidx.core.app.NotificationCompat
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
        intent.putExtra("CAR_DETAILS", car)

        if (isAppRunning()) {
            // App is running, just start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            // App is not running, show a notification
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationBuilder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.drawable.ic_menu_open)
                .setContentTitle("Notification Title")
                .setContentText(message.notification!!.body!!)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }

        Log.d(TAG, "From: " + message.from!!)
        Log.d(TAG, "Notification Message Body: " + message.notification!!.body!!)
    }

    private fun isAppRunning(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses

        for (processInfo in runningAppProcesses) {
            if (processInfo.processName == packageName) {
                return true
            }
        }

        return false
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }
}