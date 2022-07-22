package com.dicoding.githubusersubmission2.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubusersubmission2.ui.FollowersFragment
import com.dicoding.githubusersubmission2.ui.FollowingFragment

class SectionPageAdapter(activity: AppCompatActivity, private val login: Bundle): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }
        fragment?.arguments = login
        return fragment as Fragment
    }


}