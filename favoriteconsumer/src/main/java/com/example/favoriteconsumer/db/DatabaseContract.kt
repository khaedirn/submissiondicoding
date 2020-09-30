package com.example.favoriteconsumer.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    // Authority yang digunakan
    const val AUTHORITY = "com.example.gitapp"
    const val SCHEME = "content"

    /*
    Penggunaan Base Columns akan memudahkan dalam penggunaan suatu table
    Untuk id yang autoincrement sudah default ada di dalam kelas BaseColumns dengan nama field _ID
     */
    class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "id"
            const val AVATAR = "avatar"
            const val NAME = "name"
            const val USERNAME = "username"
            const val LOCATION = "location"
            const val REPO = "repo"
            const val COMPANY = "company"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val BLOG = "blog"

            // Base content yang digunakan untuk akses content provider
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}