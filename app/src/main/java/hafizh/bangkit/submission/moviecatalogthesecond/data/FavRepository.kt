package hafizh.bangkit.submission.moviecatalogthesecond.data

import android.app.Application
import androidx.paging.PagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowRoomDatabase
import java.util.concurrent.Executors

class FavRepository(application: Application) {

    companion object {
        @Volatile
        private var instance: FavRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: FavRepository(application).apply { instance = this }
            }
    }

    private val db = FavMovieTvShowRoomDatabase.getDatabase(application)
    private val favDao = db.favMovieTvShowDao()
    private val executorService = Executors.newSingleThreadExecutor()

    fun getSpecificFav(isMovie: Boolean) : PagingSource<Int, MovieTvShowEntity> = favDao.getSpecificFav(isMovie)

    fun getFavById(id: Int, isMovie: Boolean) = favDao.getFavById(id, isMovie)

    fun insert(movieTvShowEntity: MovieTvShowEntity) {
        executorService.execute { favDao.insert(movieTvShowEntity) }
    }

    fun delete(movieTvShowEntity: MovieTvShowEntity) {
        executorService.execute { favDao.delete(movieTvShowEntity) }
    }
}