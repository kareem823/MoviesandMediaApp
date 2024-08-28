package com.kidris.info5126mobileproject.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("load")
fun loadImage(view: ImageView, url:String){

    //used glide for image load ins
    Glide.with(view).load(url).into(view)

}