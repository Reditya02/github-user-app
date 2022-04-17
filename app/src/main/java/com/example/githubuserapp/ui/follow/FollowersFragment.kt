package com.example.githubuserapp.ui.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.Const
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.ui.detail.DetailUserViewModel

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowers

        val observerFollowers = Observer<ArrayList<UserResponse>> {
            Log.d(Const.TAG, "FragmentAAA " + viewModel.listFollowers.value.toString())
            showRecycler(it)
        }
        viewModel.listFollowers.observe(viewLifecycleOwner, observerFollowers)
        Log.d(Const.TAG, "FragmentAAA " + viewModel.listFollowers.value.toString())

        viewModel.isFollowLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showRecycler(list: ArrayList<UserResponse>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val adapter = FollowAdapter(list)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}