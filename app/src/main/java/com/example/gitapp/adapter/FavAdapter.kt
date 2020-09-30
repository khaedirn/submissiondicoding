package com.example.gitapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitapp.R
import com.example.gitapp.model.Favorite
import kotlinx.android.synthetic.main.item_row_user.view.*

class FavAdapter(private val listFav: ArrayList<Favorite>) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int = listFav.size

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    inner class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(img_photo)

                txt_name.text = favorite.username
                txt_repo.text = favorite.repo
                txt_following.text = favorite.following
                txt_followers.text = favorite.followers

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(favorite) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }

}