package com.example.favoriteconsumer.db

import android.database.Cursor
import com.example.favoriteconsumer.model.Favorite

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val repo = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPO))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                val blog = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BLOG))
                favList.add(
                    Favorite(
                        id,
                        avatar,
                        name,
                        username,
                        location,
                        repo,
                        company,
                        followers,
                        following,
                        blog
                    )
                )
            }
        }
        return favList
    }

    fun mapCursorToObject(favCursor: Cursor?): Favorite {
        var fav = Favorite()
        favCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
            val repo = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPO))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
            val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
            val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
            val blog = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BLOG))
            fav = Favorite(
                id,
                avatar,
                name,
                username,
                location,
                repo,
                company,
                followers,
                following,
                blog
            )
        }
        return fav
    }
}