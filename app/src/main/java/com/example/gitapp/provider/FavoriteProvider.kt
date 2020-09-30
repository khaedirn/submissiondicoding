package com.example.gitapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.gitapp.adapter.FavRoomDatabase
import com.example.gitapp.db.DatabaseContract.AUTHORITY
import com.example.gitapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.gitapp.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.gitapp.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.example.gitapp.model.Favorite

class FavoriteProvider : ContentProvider() {
    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favRoomDatabase: FavRoomDatabase
        init {

            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        favRoomDatabase = FavRoomDatabase.getDatabase(context as Context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE -> cursor = favRoomDatabase.getNoteDao().selectAll()
            FAVORITE_ID -> cursor = favRoomDatabase.getNoteDao().selectById(uri.lastPathSegment.toString().toInt())
            else -> cursor = null
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }



    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
