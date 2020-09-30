package com.example.gitapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitapp.adapter.FavDao
import com.example.gitapp.adapter.FavRoomDatabase
import com.example.gitapp.adapter.ViewPagerAdapter
import com.example.gitapp.fragment.FollowerFragment
import com.example.gitapp.fragment.FollowingFragment
import com.example.gitapp.model.Favorite
import kotlinx.android.synthetic.main.activity_fav_detail.*
import kotlinx.android.synthetic.main.activity_favorite.*

class FavDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var database: FavRoomDatabase
    private lateinit var dao: FavDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_detail)

        database = FavRoomDatabase.getDatabase(applicationContext)
        dao = database.getNoteDao()

        progressBar.visibility = View.VISIBLE
        val data = intent.getParcelableExtra(EXTRA_DATA) as Favorite
        txt_nama.text = data.name
        Glide.with(this)
            .load(data.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(imageView)
        txt_company.text = data.company
        txt_lokasi.text = data.location
        txt_repos.text = "${data.repo} Repository"
        progressBar.visibility = View.INVISIBLE

        setupViewPager(viewpager)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val data = intent.getParcelableExtra(EXTRA_DATA) as Favorite
                dao.delete(data)
                finish()
                Toast.makeText(applicationContext, "Favorite account removed", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu2 -> {
                true
            }
            else -> true
        }
    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.populateFragment(FollowerFragment(), "Follower")
        adapter.populateFragment(FollowingFragment(), "Following")
        viewpager.adapter = adapter
        tab_layout_profile2.setupWithViewPager(viewpager)
    }
}