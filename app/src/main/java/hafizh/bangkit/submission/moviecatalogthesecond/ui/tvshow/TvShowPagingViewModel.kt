package hafizh.bangkit.submission.moviecatalogthesecond.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.TvShowPagingSource

class TvShowPagingViewModel (private val pagingSource: TvShowPagingSource) : ViewModel() {

    val tvShows = Pager(
        config = PagingConfig(
            pageSize = 20, maxSize = 200, prefetchDistance = 5
        )
    ) {
        pagingSource
    }.flow.cachedIn(viewModelScope)
}