package com.example.gitapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.model.User
import com.example.gitapp.adapter.UserAdapter
import com.example.gitapp.model.FollowerViewModel
import com.example.gitapp.model.FollowingViewModel
import com.example.gitapp.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchview.setOnQueryTextListener(this)

        mainViewModel.getUser().observe(this, Observer { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val move = Intent(this@MainActivity, DetailActivity::class.java)
                move.putExtra(DetailActivity.EXTRA_DATA, data)
                startActivity(move)

                val mFollowerViewModel = FollowerViewModel
                val datas = data.username
                mFollowerViewModel.DATA = datas.toString()

                val mFollowingViewModel = FollowingViewModel
                val datak = data.username
                mFollowingViewModel.DATA = datak.toString()
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchview.onActionViewExpanded()
        showLoading(true)
        mainViewModel.setUser(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val e = Intent(this, FavoriteActivity::class.java)
                startActivity(e)
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}
