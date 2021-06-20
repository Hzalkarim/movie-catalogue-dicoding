package hafizh.bangkit.submission.moviecatalogthesecond.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow

class FavViewModel(private val favRepository: FavRepository) : ViewModel() {

    val movieFav : Flow<PagingData<MovieTvShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20, maxSize = 200, prefetchDistance = 5
        )
    ) {
        favRepository.getSpecificFav(true)
    }.flow.cachedIn(viewModelScope)

    val tvShowFav : Flow<PagingData<MovieTvShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20, maxSize = 200, prefetchDistance = 5
        )
    ) {
        favRepository.getSpecificFav(false)
    }.flow.cachedIn(viewModelScope)

    fun addFav(movieTvShowEntity: MovieTvShowEntity) {
        favRepository.insert(movieTvShowEntity)
    }

    fun removeFav(movieTvShowEntity: MovieTvShowEntity) {
        favRepository.delete(movieTvShowEntity)
    }

    fun getFavById(id: Int, isMovie: Boolean, consumer: (MovieTvShowEntity?) -> Unit) {
        favRepository.getFavById(id, isMovie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, Throwable::printStackTrace)
    }


}