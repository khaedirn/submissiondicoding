package com.example.gitapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.R
import com.example.gitapp.adapter.UserAdapter
import com.example.gitapp.model.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        progressBar.visibility = View.VISIBLE
        rc_follower.layoutManager = LinearLayoutManager(context)
        rc_follower.adapter = adapter
        rc_follower.isNestedScrollingEnabled

        followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
        followerViewModel.setUser()
        followerViewModel.getUser().observe(viewLifecycleOwner, Observer { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
}
