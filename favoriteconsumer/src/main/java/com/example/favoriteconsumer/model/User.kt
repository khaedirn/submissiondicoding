package com.example.favoriteconsumer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var avatar: String? = null,
    var name: String? = null,
    var username: String? = null,
    var location: String? = null,
    var repo: String? = null,
    var company: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var blog: String? = null


) : Parcelable