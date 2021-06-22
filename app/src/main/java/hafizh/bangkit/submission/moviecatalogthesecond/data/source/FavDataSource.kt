package hafizh.bangkit.submission.moviecatalogthesecond.data.source

import android.app.Application
import androidx.paging.PagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowRoomDatabase
import java.util.concurrent.Executors

open class FavDataSource(application: Application) {

    companion object {
        @Volatile
        private var instance: FavDataSource? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: FavDataSource(application).apply { instance = this }
            }
    }

    private val db = FavMovieTvShowRoomDatabase.getDatabase(application)
    private val favDao = db.favMovieTvShowDao()

    val dao get() = favDao
}