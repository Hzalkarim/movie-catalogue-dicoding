package hafizh.bangkit.submission.moviecatalogthesecond.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class TvShowPagingSource(private val apiService: MovieTvShowApiService) : RxPagingSource<Int, TvShowResponse>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TvShowResponse>> {
        val now = params.key ?: 1
        return apiService
            .getPopularTvShows(now)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, TvShowResponse>> { result ->
                LoadResult.Page(
                    data = result.tvShows,
                    prevKey = if (params.key == 1) null else params.key?.minus(1),
                    nextKey = now + 1
                )
            }
            .onErrorReturn { e ->
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}