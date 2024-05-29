package android.tvz.hr.listadobrinic.local

import android.content.Context
import androidx.room.Room

object CarDatabaseHelper {
    private var db: CarDatabase? = null

    fun getInstance(context: Context): CarDatabase {
        if(db == null){
            db = Room.databaseBuilder(
                context.applicationContext,
                CarDatabase::class.java, "cars"
            ).allowMainThreadQueries().build()
        }
        return db!!
    }
}