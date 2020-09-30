package com.example.favoriteconsumer.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.favoriteconsumer.*
import com.example.favoriteconsumer.model.Favorite
import com.example.favoriteconsumer.model.FollowerViewModel
import com.example.favoriteconsumer.model.FollowingViewModel
import kotlinx.android.synthetic.main.item_row_user.view.*

class FavAdapter(private val activity: Activity) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    var listFav = ArrayList<Favorite>()
    set(listFav) {
        this.listFav.clear()
        this.listFav.addAll(listFav)
        notifyDataSetChanged()
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

                cv_favorite.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object :
                            CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, FavDetailActivity::class.java)
                                intent.putExtra(FavDetailActivity.EXTRA_DATA, favorite)
                                intent.putExtra(FavDetailActivity.EXTRA_POSITION, position)
                                activity.startActivityForResult(
                                    intent,
                                    FavDetailActivity.REQUEST_UPDATE
                                )

                                val mFollowerViewModel =
                                    FollowerViewModel
                                val datas = favorite.username
                                mFollowerViewModel.DATA = datas.toString()

                                val mFollowingViewModel =
                                    FollowingViewModel
                                val datak = favorite.username
                                mFollowingViewModel.DATA = datak.toString()

                            }
                        })
                )
            }
        }
    }

}