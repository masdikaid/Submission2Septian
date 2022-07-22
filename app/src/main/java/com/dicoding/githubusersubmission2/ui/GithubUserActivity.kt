package com.dicoding.githubusersubmission2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusersubmission2.R
import com.dicoding.githubusersubmission2.adapter.SearchAdapter
import com.dicoding.githubusersubmission2.databinding.ActivityGithubUserBinding
import com.dicoding.githubusersubmission2.networking.GithubUser
import com.dicoding.githubusersubmission2.utils.Helper
import com.dicoding.githubusersubmission2.viewmodel.UserViewModel

class GithubUserActivity : AppCompatActivity() {

    private var _binding: ActivityGithubUserBinding? = null
    private val binding get() = _binding!!
    private val helper = Helper()

    private val listUser = ArrayList<GithubUser>()
    private lateinit var adapter: SearchAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter(listUser)
        val layoutManager = LinearLayoutManager(this@GithubUserActivity)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapter

        val userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)

        userViewModel.listGithubUser.observe(this) { listGithubUser ->
            setUserData(listGithubUser)
        }
//
        userViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar)
        }
//
        userViewModel.totalCount.observe(this) {
            showText(it)
        }
    }



//    fungsi mencari user ketika ada sebuah aksi pada action bar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        val userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    userViewModel.searchGithubUser(it)
                }
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

//    fungsi untuk set data dari API ke Tampilan UI

    private fun setUserData(listGithubUser: List<GithubUser>){
        listUser.clear()
        listUser.addAll(listGithubUser)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubUser){
                showSelectedUser(data)
            }
        })
    }

//    fungsi untuk menampilkan detail user ketika di click

    private fun showSelectedUser(data: GithubUser){
        val moveWithParcelableIntent = Intent(this@GithubUserActivity, UserDetailActivity::class.java)
        moveWithParcelableIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }

//    fungsi untuk menampilkan atau hide text view dari totalcount

    private fun showText(totalCount: Int){
        with(binding){
            if(totalCount == 0){
                tvNotice.visibility = View.VISIBLE
                tvNotice.text = resources.getString(R.string.user_not_found)
            } else {
                tvNotice.visibility = View.INVISIBLE
            }
        }
    }


    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

