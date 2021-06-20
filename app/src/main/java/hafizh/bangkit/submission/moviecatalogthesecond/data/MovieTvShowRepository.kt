package hafizh.bangkit.submission.moviecatalogthesecond.data

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.RemoteDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse
import io.reactivex.rxjava3.core.Observable

open class MovieTvShowRepository (private val remoteDataSource: RemoteDataSource) {

    companion object {
        @Volatile
        var instance : MovieTvShowRepository? = null

        fun getInstance(dataSource: RemoteDataSource) =
            instance ?: synchronized(this) {
                instance ?: MovieTvShowRepository(dataSource).apply {
                    instance = this
                }
            }
    }

    fun getTvShowObservable(listId: List<Int>) : Observable<TvShowResponse> {
        val list = listId.map { remoteDataSource.getTvShowObservable(it) }
        return Observable.merge(list)
    }

    fun getMovieObservable(listId: List<Int>) : Observable<MovieResponse> {
        val list = listId.map { remoteDataSource.getMovieObservable(it) }
        return Observable.merge(list)
    }
}