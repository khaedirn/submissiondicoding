package com.example.favoriteconsumer

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.favoriteconsumer.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.favoriteconsumer.adapter.ViewPagerAdapter
import com.example.favoriteconsumer.model.Favorite
import com.example.gitapp.fragment.FollowerFragment
import com.example.gitapp.fragment.FollowingFragment
import kotlinx.android.synthetic.main.activity_fav_detail.*

class FavDetailActivity : AppCompatActivity() {
    private var favorite: Favorite? = null
    private var position: Int = 0
    private lateinit var uriWithId: Uri

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_UPDATE = 200
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_detail)

        supportActionBar?.title = "Favorite Detail"

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite?.id)

        progressBar.visibility = View.VISIBLE
        favorite = intent.getParcelableExtra(EXTRA_DATA)
        if (favorite != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
        } else {
            favorite = Favorite()
        }

        favorite?.let { txt_nama.text = (it.name) + "Repository" }
        favorite?.let { txt_company.text = (it.company) }
        favorite?.let { txt_lokasi.text = (it.location) }
        favorite?.let { txt_repos.text = (it.repo) }

        val avatar = favorite?.avatar
        Glide.with(this)
            .load(avatar)
            .apply(RequestOptions().override(55, 55))
            .into(imageView)

        progressBar.visibility = View.INVISIBLE

        setupViewPager(viewpager)

    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.populateFragment(FollowerFragment(), "Follower")
        adapter.populateFragment(FollowingFragment(), "Following")
        viewpager.adapter = adapter
        tab_layout_profile2.setupWithViewPager(viewpager)
    }
}