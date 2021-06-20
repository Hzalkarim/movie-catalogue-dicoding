package hafizh.bangkit.submission.moviecatalogthesecond.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.MoviePagingSource

class MoviePagingViewModel(private val moviePagingSource: MoviePagingSource) : ViewModel() {

    val movies = Pager(
        config = PagingConfig(
            pageSize = 20, maxSize = 200, prefetchDistance = 5
        )
    ) {
        moviePagingSource
    }.flow.cachedIn(viewModelScope)
}