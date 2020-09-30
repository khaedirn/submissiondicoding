package com.example.gitapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoriteconsumer.model.FollowingViewModel
import com.example.favoriteconsumer.R
import com.example.favoriteconsumer.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        rc_following.layoutManager = LinearLayoutManager(context)
        rc_following.adapter = adapter
        rc_following.isNestedScrollingEnabled
        progressBar.visibility = View.VISIBLE

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setUser()
        followingViewModel.getUser().observe(viewLifecycleOwner, Observer { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
}
