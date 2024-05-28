package android.tvz.hr.listadobrinic

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.tvz.hr.listadobrinic.api.CarApi
import android.tvz.hr.listadobrinic.api.ServiceGenerator
import android.tvz.hr.listadobrinic.model.Car
import android.util.Log
import android.widget.RemoteViews
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LatestPictureWidget : AppWidgetProvider() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for(appWidgetId in appWidgetIds){
            GlobalScope.launch(Dispatchers.IO){
                if (context != null) {
                    updateAppWidget(context,appWidgetManager,appWidgetId)
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }
}

internal suspend fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
){
    val BASE_URL = "http://10.0.2.2:3000"

    val client: CarApi = ServiceGenerator().createService(CarApi::class.java,BASE_URL)

    val carsCall: Call<Car> = client.getLatestCar()

    carsCall.enqueue(object : Callback<Car> {
        override fun onResponse(
            call: Call<Car>,
            response: Response<Car>
        ) {
            if(response.isSuccessful){
                val car = response.body() as Car

                val views = RemoteViews(context.packageName, R.layout.latest_picture_widget)

                val title = car.brand + " " + car.model

                GlobalScope.launch(Dispatchers.IO) {
                    val bitmap = withContext(Dispatchers.IO){
                        val loader = ImageLoader(context)
                        val request = ImageRequest.Builder(context)
                            .data(car.imageUrl)
                            .allowHardware(false)
                            .build()
                        val result = (loader.execute(request) as SuccessResult).drawable
                        (result as BitmapDrawable).bitmap
                    }

                    withContext(Dispatchers.Main) {
                        views.setTextViewText(R.id.widget_title, title)
                        views.setImageViewBitmap(R.id.widget_image, bitmap)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            }
        }

        override fun onFailure(call: Call<Car>, t: Throwable) {
            Log.d("APP FETCH ", "ERROR ON LATEST CAR FETCH")
        }
    })
}