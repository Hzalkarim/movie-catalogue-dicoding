package hafizh.bangkit.submission.moviecatalogthesecond.ui.fav

import hafizh.bangkit.submission.moviecatalogthesecond.data.FavRepository
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
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class FavViewModelTest : TestCase() {

    @Mock
    private lateinit var dao: FavMovieTvShowDao

    @Mock
    private lateinit var apiService: MovieTvShowApiService

    @Mock
    private lateinit var consumer: (MovieTvShowEntity?) -> Unit
    private lateinit var favRepository: FavRepository
    private lateinit var favViewModel: FavViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        favRepository = FavRepository(dao)
        favViewModel = FavViewModel(favRepository)
    }

    @Test
    fun testAddFav() {
        val dummy = MovieTvShowEntity(id = 1)
        Mockito.`when`(dao.insert(dummy)).thenAnswer { i -> i }
        favViewModel.addFav(dummy)
        Thread.sleep(1000)
        Mockito.verify(dao).insert(dummy)
    }

    @Test
    fun testRemoveFav() {
        val dummy = MovieTvShowEntity(id = 1)
        Mockito.`when`(dao.delete(dummy)).thenAnswer { i -> i }
        favViewModel.removeFav(dummy)
        Thread.sleep(1000)
        Mockito.verify(dao).delete(dummy)
    }

    @Test
    fun testGetFavById() {
        val dummy = MovieTvShowEntity(id = 1)
        Mockito.`when`(dao.getFavById(1, true)).thenReturn(Single.just(dummy))
        try {
            favViewModel.getFavById(1, true, consumer)
        } catch (e: ExceptionInInitializerError) {
            //nothing
        }
        Mockito.verify(dao).getFavById(1, true)
    }
}