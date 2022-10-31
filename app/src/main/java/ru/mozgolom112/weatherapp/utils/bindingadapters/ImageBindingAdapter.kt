package ru.mozgolom112.weatherapp.utils.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, icon_url: String?) {
    if (icon_url != null) {
        val url = "https://yastatic.net/weather/i/icons/funky/dark/$icon_url.svg"
        url.also {
            if (it.lowercase().endsWith("svg")) {
                val imageLoader = ImageLoader.Builder(imageView.context)
                    .componentRegistry {
                        add(SvgDecoder(imageView.context))
                    }.build()
                val request = ImageRequest.Builder(imageView.context).apply {
                    //error(errorImg)
                    //placeholder(errorImg)
                    data(it).decoder(SvgDecoder(imageView.context))
                }.target(imageView).build()
                imageLoader.enqueue(request)
            } else {
                val imageLoader = ImageLoader(imageView.context)
                val request = ImageRequest.Builder(imageView.context).apply {
//                    if (cache) {
//                        memoryCachePolicy(CachePolicy.ENABLED)
//                    } else {
//                        memoryCachePolicy(CachePolicy.DISABLED)
//                    }
//                    error(errorImg)
//                    placeholder(errorImg)
                    data("$it")
                }.target(imageView).build()
                imageLoader.enqueue(request)
            }
        }
    }
}

@BindingAdapter("imageDrawable")
fun setImageUrl(imageView: ImageView, drawableIcon: Int) = imageView.setImageResource(drawableIcon)