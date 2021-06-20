package hafizh.bangkit.submission.moviecatalogthesecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.MovietvListItemBinding
import hafizh.bangkit.submission.moviecatalogthesecond.utils.TmdbImageHelper

class MoviePagingAdapter : PagingDataAdapter<MovieResponse, MoviePagingAdapter.MoviePagingViewHolder>(MoviePagingComparator) {

    private var onClickListener: OnClickCallbackMovieTvItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagingViewHolder {
        return MoviePagingViewHolder(
            MovietvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviePagingViewHolder, pos: Int) {
        with (holder.binding) {
            tvTitle.text = getItem(pos)?.title
            tvDesc.text = getItem(pos)?.overview
            Glide.with(holder.itemView.context)
                .load(TmdbImageHelper.getImage(getItem(pos)?.posterPath))
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .into(imgPoster)

        }

        if (onClickListener != null)
            holder.itemView.setOnClickListener { onClickListener?.onClickCallback(getItem(pos) as BaseResponse)}
    }

    fun setOnClickCallback(listener: OnClickCallbackMovieTvItem) {
        onClickListener = listener
    }

    object MoviePagingComparator : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean =
            oldItem == newItem

    }

    class MoviePagingViewHolder(val binding: MovietvListItemBinding) : RecyclerView.ViewHolder(binding.root)
}