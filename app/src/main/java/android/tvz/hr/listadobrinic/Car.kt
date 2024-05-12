package android.tvz.hr.listadobrinic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(val id: String, val brand: String, val model: String, val color: String, var imageUrl: String) : Parcelable