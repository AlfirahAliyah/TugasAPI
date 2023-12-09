package com.dicoding.mygithub2.ui.page3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithub2.data.local.FavAkun
import com.dicoding.mygithub2.data.respone.User
import com.dicoding.mygithub2.databinding.ActivityFavoriteBinding
import com.dicoding.mygithub2.ui.page2.DetailActivity
import com.dicoding.mygithub2.ui.utama.AkunAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: AkunAdapter
    private lateinit var viewModel : FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AkunAdapter()
        adapter.notifyDataSetChanged()
//        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(FavViewModel::class.java)

        showLoading(true)

        adapter.setOnItemClickCallback(object : AkunAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, data.login)
                    it.putExtra(DetailActivity.ID, data.id)
                    it.putExtra(DetailActivity.AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.adapter = adapter
            rvFav.addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity,
                    (rvFav.layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        viewModel.getFavAkun()?.observe(this) {
            showLoading(false)
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<FavAkun>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (akun in users){
            val userMapped = User(
                akun.id,
                akun.login,
                akun.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}