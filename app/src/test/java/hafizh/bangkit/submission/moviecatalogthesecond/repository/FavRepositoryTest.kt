package hafizh.bangkit.submission.moviecatalogthesecond.repository

import androidx.paging.PagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.FavDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.MoviePagingSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity.MovieTvShowEntity
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.room.FavMovieTvShowDao
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavRepositoryTest : TestCase() {

    @Mock
    private lateinit var dao: FavMovieTvShowDao

    @Mock
    private lateinit var apiService: MovieTvShowApiService

    private lateinit var favRepository: FavRepository

    @Before
    public override fun setUp() {
        super.setUp()
        favRepository = FavRepository(dao)
    }

    @Test
    fun testGetSpecificFav() {
        val dummy = MoviePagingSource(apiService)
        Mockito.`when`(dao.getSpecificFav(true)).thenReturn(dummy as PagingSource<Int, MovieTvShowEntity>)
        favRepository.getSpecificFav(true)
        Mockito.verify(dao).getSpecificFav(true)
    }

    @Test
    fun testGetFavById() {
        val dummy = MovieTvShowEntity(id = 1)
        //Mockito.`when`(dao.getFavById(1, true)).thenReturn(Single.just(dummy))
        favRepository.getFavById(1, true)
        Mockito.verify(dao).getFavById(1, true)
    }

    @Test
    fun testInsert() {
        val dummy = MovieTvShowEntity(id = 1)
        Mockito.`when`(dao.insert(dummy)).thenAnswer { i -> i }
        favRepository.insert(dummy)
        Thread.sleep(1000)
        Mockito.verify(dao).insert(dummy)
    }

    @Test
    fun testDelete() {
        val dummy = MovieTvShowEntity(id = 1)
        Mockito.`when`(dao.delete(dummy)).thenAnswer { i -> i }
        favRepository.delete(dummy)
        Thread.sleep(1000)
        Mockito.verify(dao).delete(dummy)
    }
}