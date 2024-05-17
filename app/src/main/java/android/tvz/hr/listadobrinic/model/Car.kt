package android.tvz.hr.listadobrinic.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Car (
    @PrimaryKey val id: Int,
    val brand: String,
    val model: String,
    val color: String,
    val imageUrl: String
) : Parcelable