package com.example.gitapp.adapter

import android.content.ContentValues
import android.database.Cursor
import androidx.room.*
import com.example.gitapp.model.Favorite

@Dao
interface FavDao {

    @Insert
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun delAll()

    @Query("SELECT * FROM favorite")
    fun getAll() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getById(id: Int) : List<Favorite>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getByName(username: String) : List<Favorite>

    @Query("SELECT * FROM favorite")
    fun selectAll() : Cursor

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun selectById(id: Int) : Cursor


}