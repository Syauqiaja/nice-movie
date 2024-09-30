package com.brighton.nicemovie.presentation.features.home.adapter

import android.app.LauncherActivity.ListItem
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brighton.nicemovie.databinding.ItemMovieBinding
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.utils.scaleViewOneShot
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class MovieAdapter(private val listItem: ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    var onItemClick: ((Movie) -> Unit)? = null
    fun submitData(items: List<Movie>){
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listItem[position]
        holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvDesc.text = movie.year;

            Glide.with(itemView)
                .asBitmap()
                .load(movie.poster)
                .placeholder(shimmerDrawable)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.ivMovie.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            binding.root.setOnClickListener {
                onItemClick?.let {
                    binding.root.scaleViewOneShot(0.95f, 100L)
                    onItemClick?.invoke(movie)
                }
            }
        }
    }

    private val shimmerBuilder = Shimmer.AlphaHighlightBuilder()
        .setDuration(1000) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.7f) //the alpha of the underlying children
        .setHighlightAlpha(0.6f) // the shimmer alpha amount
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmerBuilder)
    }
}