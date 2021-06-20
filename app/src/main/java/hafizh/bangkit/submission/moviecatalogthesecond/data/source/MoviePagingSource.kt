package hafizh.bangkit.submission.moviecatalogthesecond.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val apiService: MovieTvShowApiService) : RxPagingSource<Int, MovieResponse>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieResponse>> {
        val now = params.key ?: 1
        return apiService
                .getPopularMovies(now)
                .subscribeOn(Schedulers.io())
                .map<LoadResult<Int, MovieResponse>> { result ->
                    LoadResult.Page(
                        data = result.movies,
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

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
