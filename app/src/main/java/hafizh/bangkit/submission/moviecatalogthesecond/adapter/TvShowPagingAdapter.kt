package hafizh.bangkit.submission.moviecatalogthesecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.MovietvListItemBinding
import hafizh.bangkit.submission.moviecatalogthesecond.utils.TmdbImageHelper

class TvShowPagingAdapter :
    PagingDataAdapter<TvShowResponse, TvShowPagingAdapter.TvShowPagingViewHolder>(TvShowPagingComparator) {
    private var onClickListener: OnClickCallbackMovieTvItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowPagingViewHolder {
        return TvShowPagingViewHolder(
            MovietvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TvShowPagingViewHolder, pos: Int) {
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

    class TvShowPagingViewHolder (val binding: MovietvListItemBinding) : RecyclerView.ViewHolder(binding.root)

    object TvShowPagingComparator : DiffUtil.ItemCallback<TvShowResponse>() {
        override fun areItemsTheSame(oldItem: TvShowResponse, newItem: TvShowResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShowResponse, newItem: TvShowResponse): Boolean =
            oldItem == newItem

    }
}