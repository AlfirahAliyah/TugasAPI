package com.dicoding.mygithub2.ui.page2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.mygithub2.R
import com.dicoding.mygithub2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra(USERNAME).toString()
        val id = intent.getLongExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR).toString()
        val bundle = Bundle()
        bundle.putString(USERNAME, username)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(DetailViewModel::class.java)

        showLoading(true)
        viewModel.setDetail(username)
        viewModel.cariDetail().observe(this) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivDAkun)
                tvDAkun.text = it.nama
                tvDId.text = "@${it.login}"
                tvFollower.text = resources.getString(R.string.tab_text_1).format(it.followers)
                tvFollowing.text = resources.getString(R.string.tab_text_2).format(it.following)
                showLoading(false)
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = viewModel.checkAkun(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0) {
                        _isChecked = true
                        binding.toggleFav.isChecked = true
                    }else{
                        _isChecked = false
                        binding.toggleFav.isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            binding.toggleFav.isChecked = _isChecked

            if (_isChecked) {
                viewModel.addFavorite(username, id, avatarUrl)
            } else {
                viewModel.removeFavorite(id)
            }
        }


        val sectionAdapter = SectionAdapter(this, bundle)
        binding.apply {
            val viewPager: ViewPager2 = vpDetail
            viewPager.adapter = sectionAdapter
        }

        TabLayoutMediator(binding.tab2, binding.vpDetail) { tab, position ->
            tab.text = when(position){
                0 -> "Followers"
                1 -> "Following"
                else -> "Unknown Tab Layout"
            }
        }.attach()

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USERNAME = "usernameMove"
        const val ID = "idMove"
        const val AVATAR = "avatarMove"
    }

}


