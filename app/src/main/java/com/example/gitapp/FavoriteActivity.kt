package com.example.gitapp

import android.content.ContentResolver
import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.adapter.FavAdapter
import com.example.gitapp.adapter.FavDao
import com.example.gitapp.adapter.FavRoomDatabase
import com.example.gitapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.gitapp.model.Favorite
import com.example.gitapp.model.FollowerViewModel
import com.example.gitapp.model.FollowingViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var database: FavRoomDatabase
    private lateinit var dao: FavDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewfav.setHasFixedSize(true)
        database = FavRoomDatabase.getDatabase(applicationContext)
        dao = database.getNoteDao()



        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        object: ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                getFavData()
            }
        }

    }


    private fun getFavData() {
        val list = arrayListOf<Favorite>()
        list.addAll(dao.getAll())
        showRecyclerList(list)

    }

    private fun showRecyclerList(list:ArrayList<Favorite>) {
        recyclerViewfav.layoutManager = LinearLayoutManager(this)
        val listFavAdapter = FavAdapter(list)
        listFavAdapter.notifyDataSetChanged()
        recyclerViewfav.adapter = listFavAdapter
        listFavAdapter.setOnItemClickCallback(object : FavAdapter.OnItemClickCallback{
            override fun onItemClicked(favorite: Favorite) {
                val move = Intent(this@FavoriteActivity, FavDetailActivity::class.java)
                move.putExtra(FavDetailActivity.EXTRA_DATA, favorite)
                startActivity(move)

                val mFollowerViewModel = FollowerViewModel
                val datas = favorite.username
                mFollowerViewModel.DATA = datas.toString()

                val mFollowingViewModel = FollowingViewModel
                val datak = favorite.username
                mFollowingViewModel.DATA = datak.toString()
            }
        })

        setRecyclerViewItemTouchListener(list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                dao.delAll()
                Toast.makeText(this, "All Favorite Successfully Deleted", Toast.LENGTH_SHORT).show()
                getFavData()
                true
            }
            R.id.menu2 -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun setRecyclerViewItemTouchListener(list: ArrayList<Favorite>) {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                list.removeAt(position)
                recyclerViewfav.adapter!!.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewfav)
    }


    override fun onResume() {
        super.onResume()
        getFavData()
    }
}

