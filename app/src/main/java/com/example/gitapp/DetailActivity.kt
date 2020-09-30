package com.example.gitapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitapp.adapter.FavDao
import com.example.gitapp.adapter.FavRoomDatabase
import com.example.gitapp.adapter.UserAdapter
import com.example.gitapp.adapter.ViewPagerAdapter
import com.example.gitapp.fragment.FollowerFragment
import com.example.gitapp.fragment.FollowingFragment
import com.example.gitapp.model.Favorite
import com.example.gitapp.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var favorite: Favorite
    private lateinit var database: FavRoomDatabase
    private lateinit var dao: FavDao
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        database = FavRoomDatabase.getDatabase(applicationContext)
        dao = database.getNoteDao()
        favorite = Favorite()

        adapter = UserAdapter()
        progressBar.visibility = View.VISIBLE


        val data = intent.getParcelableExtra(EXTRA_DATA) as User
        txt_nama.text = data.name
        Glide.with(this)
            .load(data.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(imageView)
        txt_company.text = data.company
        txt_lokasi.text = data.location
        txt_repos.text = "${data.repo} Repository"
        progressBar.visibility = View.INVISIBLE

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->

            saveFav(
                Favorite(
                    avatar = data.avatar,
                    name = data.name,
                    username = data.username,
                    location = data.location,
                    repo = data.repo,
                    company = data.company,
                    followers = data.followers,
                    following = data.following,
                    blog = data.blog
                )
            )
        }

        setupViewPager(viewpager)
    }

    private fun saveFav(favorite: Favorite) {

        if (dao.getByName(favorite.username.toString()).isEmpty()) {
            dao.insert(favorite)
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        } else {
            dao.update(favorite)
            Toast.makeText(this, "Already in favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.populateFragment(FollowerFragment(), "Follower")
        adapter.populateFragment(FollowingFragment(), "Following")
        viewpager.adapter = adapter
        tab_layout_profile1.setupWithViewPager(viewpager)
    }

}
