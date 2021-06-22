package hafizh.bangkit.submission.moviecatalogthesecond.data

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowDao
import java.util.concurrent.Executors

open class FavRepository(private val dao: FavMovieTvShowDao) {

    companion object {

        @Volatile
        private var instance : FavRepository? = null

        fun getInstance(dao: FavMovieTvShowDao) =
            instance ?: synchronized(this) {
                instance ?: FavRepository(dao).apply { instance = this }
            }
    }
    private val executorService = Executors.newSingleThreadExecutor()

    fun getSpecificFav(isMovie: Boolean) = dao.getSpecificFav(isMovie)

    fun getFavById(id: Int, isMovie: Boolean) = dao.getFavById(id, isMovie)

    fun insert(movieTvShowEntity: MovieTvShowEntity) {
        executorService.execute { dao.insert(movieTvShowEntity) }
    }


    fun delete(movieTvShowEntity: MovieTvShowEntity) {
        executorService.execute { dao.delete(movieTvShowEntity) }
    }
}

