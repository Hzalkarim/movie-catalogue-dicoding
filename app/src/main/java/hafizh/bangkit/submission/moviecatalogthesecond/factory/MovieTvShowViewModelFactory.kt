package hafizh.bangkit.submission.moviecatalogthesecond.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.MoviePagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.TvShowPagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.ApiConfig
import hafizh.bangkit.submission.moviecatalogthesecond.ui.movie.MoviePagingViewModel
import hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow.TvShowPagingViewModel

class MovieTvShowViewModelFactory (private val repository: MovieTvShowRepository) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance : MovieTvShowViewModelFactory? = null

        fun getInstance(repository: MovieTvShowRepository) : MovieTvShowViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MovieTvShowViewModelFactory(repository).apply {
                    instance = this
                }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviePagingViewModel::class.java) ->{
                val pagingSource = MoviePagingSource(ApiConfig.getApiService())
                MoviePagingViewModel(pagingSource) as T
            }
            modelClass.isAssignableFrom(TvShowPagingViewModel::class.java) -> {
                val pagingSource = TvShowPagingSource(ApiConfig.getApiService())
                TvShowPagingViewModel(pagingSource) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}