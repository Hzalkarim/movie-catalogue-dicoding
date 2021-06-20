package hafizh.bangkit.submission.moviecatalogthesecond.ui.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.FavPagingAdapter
import hafizh.bangkit.submission.moviecatalogthesecond.adapter.OnClickCallbackMovieTvItem
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.ActivityFavBinding
import hafizh.bangkit.submission.moviecatalogthesecond.di.Injection
import hafizh.bangkit.submission.moviecatalogthesecond.factory.FavViewModelFactory
import hafizh.bangkit.submission.moviecatalogthesecond.ui.detail.DetailActivity
import hafizh.bangkit.submission.moviecatalogthesecond.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavActivity : AppCompatActivity(), OnClickCallbackMovieTvItem {

    private lateinit var binding: ActivityFavBinding
    private lateinit var favViewModel: FavViewModel

    private val movieFavPagingAdapter = FavPagingAdapter()
    private val tvShowFavPagingAdapter = FavPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val isMovie = getExtraIsMovie()
        binding.tvDisplayFav.text = if (isMovie) getString(R.string.display_movie_fav) else getString(R.string.display_tvshow_fav)
        val factory = FavViewModelFactory.getInstance(Injection.provideFavRepository(application))
        favViewModel = ViewModelProvider(this, factory)[FavViewModel::class.java]

        binding.rvMovietvFav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if (isMovie) movieFavPagingAdapter else tvShowFavPagingAdapter
        }

        movieFavPagingAdapter.setOnClickCallback(this)
        tvShowFavPagingAdapter.setOnClickCallback(this)

        lifecycleScope.launch {
            EspressoIdlingResource.increment()
            favViewModel.movieFav.collectLatest {
                if (!EspressoIdlingResource.idlingResource.isIdleNow)
                    EspressoIdlingResource.decrement()
                movieFavPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            EspressoIdlingResource.increment()
            favViewModel.tvShowFav.collectLatest {
                if (!EspressoIdlingResource.idlingResource.isIdleNow)
                    EspressoIdlingResource.decrement()
                tvShowFavPagingAdapter.submitData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (getExtraIsMovie()) {
            binding.rvMovietvFav.adapter = movieFavPagingAdapter
            movieFavPagingAdapter.notifyDataSetChanged()
        } else {
            binding.rvMovietvFav.adapter = tvShowFavPagingAdapter
            tvShowFavPagingAdapter.notifyDataSetChanged()
        }
    }

    override fun onClickCallback(response: BaseResponse) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        when (response) {
            is MovieResponse -> detailIntent.putExtra(DetailActivity.EXTRA_IS_MOVIE, true)
            is TvShowResponse -> detailIntent.putExtra(DetailActivity.EXTRA_IS_MOVIE, false)
        }
        detailIntent.putExtra(DetailActivity.EXTRA_DETAIL, response)
        startActivity(detailIntent)
    }

    private fun getExtraIsMovie() = intent.getBooleanExtra(EXTRA_IS_MOVIE, true)

    companion object {
        const val EXTRA_IS_MOVIE = "is_movie"
    }
}