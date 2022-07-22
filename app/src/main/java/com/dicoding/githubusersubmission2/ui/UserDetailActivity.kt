package com.dicoding.githubusersubmission2.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubusersubmission2.R
import com.dicoding.githubusersubmission2.adapter.SectionPageAdapter
import com.dicoding.githubusersubmission2.databinding.ActivityUserDetailBinding
import com.dicoding.githubusersubmission2.networking.DetailResponse
import com.dicoding.githubusersubmission2.networking.GithubUser
import com.dicoding.githubusersubmission2.utils.Helper
import com.dicoding.githubusersubmission2.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private var helper = Helper()



    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)

        userDetailViewModel.listDetail.observe(this) { detailList ->
            setDataToView(detailList)
        }

        userDetailViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.DetProgressBar)
        }

        setTabLayoutView()

    }

//    fungsi untuk melihat tab layout

    private fun setTabLayoutView(){

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)

        val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        userDetailViewModel.getGithubUser(userIntent.login)

        val login = Bundle()
        login.putString(EXTRA_FRAGMENT, userIntent.login)

        val sectionPageAdapter = SectionPageAdapter(this, login)
        val viewPager : ViewPager2 = binding.viewPager

        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = binding.tabs
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tabs, viewPager){
            tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

//    fungsi untuk menampilkan API pada UI

    @SuppressLint("StringFormatInvalid")
    private fun setDataToView(detaiList: DetailResponse){
        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(detaiList.avatarUrl)
                .circleCrop()
                .into(imgDetUser)

            tvDetNama.text = detaiList.name ?: "No Name"
            tvDetUsername.text = detaiList.login
            tvDetCompany.text = detaiList.company ?: "No Company"
            tvDetLocation.text = detaiList.location ?: "No Location"
            tvRepository.text = resources.getString(R.string.Repository, detaiList.publicRepos)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}