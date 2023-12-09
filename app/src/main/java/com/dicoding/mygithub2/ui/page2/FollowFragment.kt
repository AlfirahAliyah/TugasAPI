package com.dicoding.mygithub2.ui.page2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithub2.R
import com.dicoding.mygithub2.databinding.FragmentFollowBinding
import com.dicoding.mygithub2.ui.utama.AkunAdapter

class FollowFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: AkunAdapter
    private lateinit var usernameF : String
    var type = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        usernameF = args?.getString(DetailActivity.USERNAME).toString()
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AkunAdapter()
        adapter.notifyDataSetChanged()


        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            rvFollow.adapter = adapter
            rvFollow.addItemDecoration(DividerItemDecoration(requireActivity(), (rvFollow.layoutManager as LinearLayoutManager).orientation))
        }
        showLoading(true)

        viewModel.getUserFollower(usernameF)
        viewModel.cariFollower().observe(requireActivity()) {
            if (it != null) {
                adapter.setList1(it)
                showLoading(false)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}