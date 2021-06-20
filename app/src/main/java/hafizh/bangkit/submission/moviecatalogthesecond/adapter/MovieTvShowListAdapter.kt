package hafizh.bangkit.submission.moviecatalogthesecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.MovietvListItemBinding
import hafizh.bangkit.submission.moviecatalogthesecond.utils.TmdbImageHelper

class MovieTvShowListAdapter : RecyclerView.Adapter<MovieTvShowListAdapter.MovieTvShowViewHolder>() {

    private val listMovieTvShow = ArrayList<BaseResponse>()
    private var onClickListener: OnClickCallbackMovieTvItem? = null

    inner class MovieTvShowViewHolder(val binding: MovietvListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvShowViewHolder {
        val view = MovietvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieTvShowViewHolder, pos: Int) {
        with (holder.binding) {
            tvTitle.text = listMovieTvShow[pos].title
            tvDesc.text = listMovieTvShow[pos].overview
            Glide.with(holder.itemView.context)
                .load(TmdbImageHelper.getImage(listMovieTvShow[pos].posterPath))
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .into(imgPoster)

        }

        if (onClickListener != null)
            holder.itemView.setOnClickListener { onClickListener?.onClickCallback(listMovieTvShow[pos])}
    }

    override fun getItemCount(): Int = listMovieTvShow.size

    fun setData(data: ArrayList<BaseResponse>) {
        listMovieTvShow.clear()
        listMovieTvShow.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnClickCallback(listener: OnClickCallbackMovieTvItem) {
        onClickListener = listener
    }
}