package hafizh.bangkit.submission.moviecatalogthesecond.db

import android.app.Application
import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.room.rxjava3.EmptyResultSetException
import androidx.test.core.app.ApplicationProvider
import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.FavDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowDao
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowRoomDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavRepositoryDatabaseTest : TestCase() {

    private lateinit var favDb : FavMovieTvShowRoomDatabase
    private lateinit var favDao: FavMovieTvShowDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        favDb = Room.inMemoryDatabaseBuilder(context, FavMovieTvShowRoomDatabase::class.java).build()
        favDao = favDb.favMovieTvShowDao()
    }

    @After
    public override fun tearDown() { favDb.close() }

    @Test
    fun test_insert_movie_and_then_getFavById_validInput() {
        val movieEntity = MovieTvShowEntity(uid = "1", id = 1, title = "Satu")
        favDao.insert(movieEntity)
        val getValue = favDao.getFavById(1, true).blockingGet()

        assertEquals(movieEntity, getValue)
    }

    @Test
    fun test_insert_movie_and_then_getFavById_InvalidInput() {
        val movieEntity = MovieTvShowEntity(uid = "1", id = 1, title = "Satu")
        favDao.insert(movieEntity)
        val getValue = try {
            favDao.getFavById(4, true).blockingGet()
        } catch (e: EmptyResultSetException) {
            null
        }
        assertEquals(null, getValue)
    }

    @Test
    fun test_insert_tvShow_and_then_getFavById_validInput() {
        val movieEntity = MovieTvShowEntity(uid = "1", id = 1, title = "Satu", isMovie = false)
        favDao.insert(movieEntity)
        val getValue = favDao.getFavById(1, false).blockingGet()

        assertEquals(movieEntity, getValue)
    }

    @Test
    fun test_insert_tvShow_and_then_getFavById_InvalidInput() {
        val movieEntity = MovieTvShowEntity(uid = "1", id = 1, title = "Satu", isMovie = false)
        favDao.insert(movieEntity)
        val getValue = try {
            favDao.getFavById(4, true).blockingGet()
        } catch (e: EmptyResultSetException) {
            null
        }
        assertEquals(null, getValue)
    }

    @Test
    fun test_insert_movie_and_then_delete_should_returnNull() {
        val movieEntity = MovieTvShowEntity(uid = "1", id = 1, title = "Satu")
        favDao.insert(movieEntity)
        favDao.delete(movieEntity)
        val getValue = try {
            favDao.getFavById(1, true).blockingGet()
        } catch (e: EmptyResultSetException) {
            null
        }
        assertEquals(null, getValue)
    }

    @Test
    fun test_insertMixMovieTv_then_getSpecificFav_movie() = runBlocking {
        val movies = listOf(
            MovieTvShowEntity(uid = "1", id = 1, title = "Satu"),
            MovieTvShowEntity(uid = "2", id = 2, title = "Dua"),
            MovieTvShowEntity(uid = "3", id = 3, title = "Tiga"),
            MovieTvShowEntity(uid = "4", id = 4, title = "Empat", isMovie = false),
            )
        movies.forEach { favDao.insert(it) }

        val result = favDao.getSpecificFav(true).load(
            PagingSource.LoadParams.Refresh(null, 5, false)
        )

        val data = (result as PagingSource.LoadResult.Page).data

        for (i in 0..2) {
            assertEquals(movies[i], data[i])
        }
    }

    @Test
    fun test_insertMixMovieTv_then_getSpecificFav_tvShow() = runBlocking {
        val movies = listOf(
            MovieTvShowEntity(uid = "1", id = 1, title = "Satu", isMovie = false),
            MovieTvShowEntity(uid = "3", id = 3, title = "Tiga", isMovie = false),
            MovieTvShowEntity(uid = "2", id = 2, title = "Dua"),
            MovieTvShowEntity(uid = "4", id = 4, title = "Empat"),
        )
        movies.forEach { favDao.insert(it) }

        val result = favDao.getSpecificFav(false).load(
            PagingSource.LoadParams.Refresh(null, 5, false)
        )

        val data = (result as PagingSource.LoadResult.Page).data

        for (i in listOf(0, 1)) {
            assertEquals(movies[i], data[i])
        }
    }
}