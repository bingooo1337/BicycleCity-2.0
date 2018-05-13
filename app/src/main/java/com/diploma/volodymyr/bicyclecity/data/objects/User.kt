package com.diploma.volodymyr.bicyclecity.data.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var email: String = "",
                var firstName: String = "",
                var lastName: String = "",
                var mobileNumber: String = "") : Parcelable