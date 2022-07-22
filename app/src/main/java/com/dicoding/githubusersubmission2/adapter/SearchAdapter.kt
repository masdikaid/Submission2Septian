package com.dicoding.githubusersubmission2.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubusersubmission2.databinding.ItemUserBinding
import com.dicoding.githubusersubmission2.networking.GithubUser


class SearchAdapter(private val listUser: List<GithubUser>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        with(holder.binding){
             Glide.with(root.context)
                 .load(user.avatarUrl)
                 .circleCrop()
                 .into(imgUser)
             tvName.text = user.login

             root.setOnClickListener{
                 onItemClickCallback.onItemClicked(listUser[position])
             }
        }

    }

    override fun getItemCount(): Int = listUser.size


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }


}