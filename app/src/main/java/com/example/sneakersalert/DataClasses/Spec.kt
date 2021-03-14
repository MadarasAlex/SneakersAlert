package com.example.sneakersalert.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spec(var pic:Int?,var text:String):Parcelable {}
