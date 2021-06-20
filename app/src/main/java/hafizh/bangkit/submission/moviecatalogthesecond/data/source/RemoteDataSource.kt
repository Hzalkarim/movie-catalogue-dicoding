package hafizh.bangkit.submission.moviecatalogthesecond.data.source

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService

open class RemoteDataSource (private val client: MovieTvShowApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(client: MovieTvShowApiService) : RemoteDataSource =
            instance ?: synchronized( this) {
                instance ?: RemoteDataSource(client).apply { instance = this }
            }
    }

    fun getMovieObservable(id: Int) = client.getMovie(id)

    fun getTvShowObservable(id: Int) = client.getTvShow(id)
}