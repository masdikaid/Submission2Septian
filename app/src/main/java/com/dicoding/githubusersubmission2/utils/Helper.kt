package com.dicoding.githubusersubmission2.utils

import android.view.View

class Helper {

//fungsi untuk menampilkan progress bar

    fun showLoading(isLoading: Boolean, view: View){
        if(isLoading){
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

}