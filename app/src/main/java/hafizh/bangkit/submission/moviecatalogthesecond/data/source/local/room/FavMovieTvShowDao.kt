package hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface FavMovieTvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieTvShowEntity: MovieTvShowEntity)

    @Delete
    fun delete(movieTvShowEntity: MovieTvShowEntity)

    @Query("SELECT * FROM fav_table ORDER BY id ASC ")
    fun getAllFav() : PagingSource<Int, MovieTvShowEntity>

    @Query("SELECT * FROM fav_table WHERE is_movie = :isMovie")
    fun getSpecificFav(isMovie: Boolean) : PagingSource<Int, MovieTvShowEntity>

    @Query("SELECT * FROM fav_table WHERE id = :id AND is_movie = :isMovie")
    fun getFavById(id: Int, isMovie: Boolean) : Single<MovieTvShowEntity?>
}