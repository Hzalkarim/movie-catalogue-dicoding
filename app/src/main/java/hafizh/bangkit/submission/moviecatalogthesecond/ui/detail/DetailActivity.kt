package hafizh.bangkit.submission.moviecatalogthesecond.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.room.rxjava3.EmptyResultSetException
import com.bumptech.glide.Glide
import hafizh.bangkit.submission.moviecatalogthesecond.R
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import hafizh.bangkit.submission.moviecatalogthesecond.databinding.ActivityDetailBinding
import hafizh.bangkit.submission.moviecatalogthesecond.di.Injection
import hafizh.bangkit.submission.moviecatalogthesecond.factory.DetailViewModelFactory
import hafizh.bangkit.submission.moviecatalogthesecond.factory.FavViewModelFactory
import hafizh.bangkit.submission.moviecatalogthesecond.ui.fav.FavViewModel
import hafizh.bangkit.submission.moviecatalogthesecond.utils.Mapper
import hafizh.bangkit.submission.moviecatalogthesecond.utils.TmdbImageHelper
import kotlin.NullPointerException

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_IS_MOVIE = "is_movie"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel
    private lateinit var favViewModel : FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favViewModel = ViewModelProvider(this, FavViewModelFactory.getInstance(Injection.provideFavRepository(application)))
            .get(FavViewModel::class.java)

        binding.btnFavControlDetail.isEnabled = false
        try {
            val movieTv = getParcelable()!!
            val isMovie = intent.getBooleanExtra(EXTRA_IS_MOVIE, true)
            initViewModel(movieTv)
            favViewModel.getFavById(movieTv.id, isMovie) {
                viewModel.isFavorite = true
                binding.btnFavControlDetail.text = getString(R.string.del_fav)
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
            binding.tvTitleDetail.text = getString(R.string.no_data)
        } catch (e: EmptyResultSetException) {
            binding.btnFavControlDetail.text = getString(R.string.add_fav)
        } finally {
            binding.btnFavControlDetail.isEnabled = true
        }
    }

    private fun initViewModel(baseResponse: BaseResponse) {

        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory.getInstance(Injection.provideDetailRepository(baseResponse))
        )
            .get(DetailViewModel::class.java)


        viewModel.data.observe(this, {
            when (it) {
                is MovieResponse -> setMovieDisplay(it)
                is TvShowResponse -> setTvShowDisplay(it)
            }
        })
        viewModel.setLiveData()

        binding.btnFavControlDetail.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        try {
            val movieTv = getParcelable()
            viewModel.setLiveData(movieTv!!)
        } catch (e: NullPointerException ){
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_fav_control_detail -> {
                Log.d("Tekan Add/Remove", "Berhasil Tekan")
                try {
                    binding.btnFavControlDetail.isEnabled = false
                    val entity = Mapper.baseResponseToEntity(viewModel.data.value!!)!!
                    Log.d("ENTITY TO SAVE/REMOVE", entity.toString())
                    if (viewModel.isFavorite) {
                        favViewModel.removeFav(entity)
                        binding.btnFavControlDetail.text = getString(R.string.add_fav)
                        viewModel.isFavorite = false
                    } else {
                        favViewModel.addFav(entity)
                        binding.btnFavControlDetail.text = getString(R.string.del_fav)
                        viewModel.isFavorite = true
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                } finally {
                    binding.btnFavControlDetail.isEnabled = true
                }

            }
        }
    }

    private fun getParcelable() : BaseResponse? =
        if (!intent.hasExtra(EXTRA_DETAIL))
            null
        else
            intent.getParcelableExtra(EXTRA_DETAIL)

    private fun setTvShowDisplay(tvShow: TvShowResponse) {
        with(binding) {
            tvTitleDetail.text = tvShow.title
            tvTaglineDetail.text = getString(R.string.tagline_template, tvShow.tagline)
            tvOverviewDetail.text = tvShow.overview
            tvPopularityDetail.text = getString(R.string.popularity_label, tvShow.popularity)

            ratingStar.rating = tvShow.voteAverage / 2f
            ratingNum.text = tvShow.voteAverage.toString()

            if (tvShow.posterPath != null) {
                Glide.with(this@DetailActivity)
                    .load(TmdbImageHelper.getImage(tvShow.posterPath, TmdbImageHelper.POSTER))
                    .fitCenter()
                    .placeholder(R.drawable.ic_baseline_local_movies_24)
                    .into(binding.imgPosterDetail)
            }
        }
    }

    private fun setMovieDisplay(movie: MovieResponse) {
        binding.tvTitleDetail.text = movie.title
        binding.tvTaglineDetail.text = getString(R.string.tagline_template, movie.tagline)
        binding.tvOverviewDetail.text = movie.overview
        binding.tvPopularityDetail.text = getString(R.string.popularity_label, movie.popularity)

        binding.ratingNum.text = movie.voteAverage.toString()
        binding.ratingStar.rating = movie.voteAverage / 2f

        if (movie.posterPath != null) {
            Glide.with(this)
                .load(TmdbImageHelper.getImage(movie.posterPath, TmdbImageHelper.POSTER))
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .into(binding.imgPosterDetail)
        }
    }
}