package hafizh.bangkit.submission.moviecatalogthesecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.MovietvListItemBinding
import hafizh.bangkit.submission.moviecatalogthesecond.utils.Mapper
import hafizh.bangkit.submission.moviecatalogthesecond.utils.TmdbImageHelper

class FavPagingAdapter : PagingDataAdapter<MovieTvShowEntity, FavPagingAdapter.FavViewHolder>(FavComparator) {

    private var onClickListener: OnClickCallbackMovieTvItem? = null

    override fun onBindViewHolder(holder: FavViewHolder, pos: Int) {
        with (holder.binding) {
            tvTitle.text = getItem(pos)?.title
            tvDesc.text = getItem(pos)?.overview
            Glide.with(holder.itemView.context)
                .load(TmdbImageHelper.getImage(getItem(pos)?.posterPath))
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .into(imgPoster)

        }

        if (onClickListener != null){
            try {
                val response = Mapper.entityToBaseResponse(getItem(pos)!!)
                holder.itemView.setOnClickListener { onClickListener?.onClickCallback(response)}
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            MovietvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setOnClickCallback(listener: OnClickCallbackMovieTvItem) {
        onClickListener = listener
    }

    class FavViewHolder(val binding: MovietvListItemBinding) : RecyclerView.ViewHolder(binding.root)

    object FavComparator : DiffUtil.ItemCallback<MovieTvShowEntity>() {

        override fun areItemsTheSame(
            oldItem: MovieTvShowEntity,
            newItem: MovieTvShowEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieTvShowEntity,
            newItem: MovieTvShowEntity
        ): Boolean = oldItem == newItem
    }
}