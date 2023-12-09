package com.dicoding.mygithub2.ui.utama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithub2.R
import com.dicoding.mygithub2.data.respone.User
import com.dicoding.mygithub2.databinding.ActivityMainBinding
import com.dicoding.mygithub2.settings.SettingPreferences
import com.dicoding.mygithub2.settings.ViewModelFactory
import com.dicoding.mygithub2.settings.dataStore
import com.dicoding.mygithub2.ui.page2.DetailActivity
import com.dicoding.mygithub2.ui.page3.DarkModeActivity
import com.dicoding.mygithub2.ui.page3.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel: AkunViewModel
    private lateinit var adapter: AkunAdapter
    private val username = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AkunAdapter()
        adapter.notifyDataSetChanged()


        adapter.setOnItemClickCallback(object : AkunAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, data.login)
                    it.putExtra(DetailActivity.ID, data.id)
                    it.putExtra(DetailActivity.AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        val pref = SettingPreferences.getInstance(application.dataStore)

        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(AkunViewModel::class.java)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.apply {
            rvAkun.layoutManager = LinearLayoutManager(this@MainActivity)
            rvAkun.adapter = adapter
            rvAkun.addItemDecoration(DividerItemDecoration(this@MainActivity, (rvAkun.layoutManager as LinearLayoutManager).orientation))

            viewModel.setUser(username)

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite_menu -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.theme_menu -> {
                        val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                        startActivity(intent)
                    }}
                true
            }
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener{ textView, actionId, event ->
                searchView.hide()
                viewModel.setUser(searchView.text.toString())
                showLoading(true)
                false
            }
        }

        viewModel.cariUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

