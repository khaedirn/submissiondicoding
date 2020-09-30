package com.example.gitapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Entity annotation to specify the table's name
@Entity(tableName = "favorite")
//Parcelable annotation to make parcelable object
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "avatar") var avatar: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "username") var username: String? = null,
    @ColumnInfo(name = "location") var location: String? = null,
    @ColumnInfo(name = "repo") var repo: String? = null,
    @ColumnInfo(name = "company") var company: String? = null,
    @ColumnInfo(name = "followers") var followers: String? = null,
    @ColumnInfo(name = "following") var following: String? = null,
    @ColumnInfo(name = "blog") var blog: String? = null
) : Parcelable