package hafizh.bangkit.submission.moviecatalogthesecond.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import hafizh.bangkit.submission.moviecatalogthesecond.data.MovieTvShowRepository
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.RemoteDataSource
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.MovieTvShowApiService
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.utils.RxImmediateSchedulerRule
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest : TestCase() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var apiService: MovieTvShowApiService

    private lateinit var repository: MovieTvShowRepository


    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        val dataSource = RemoteDataSource(apiService)
        repository = MovieTvShowRepository(dataSource)
    }

    @Test
    fun testRepositoryGetMovie() {

        val movie = MovieResponse("Reservoir Dogs")

        Mockito.`when`(apiService.getMovie(500)).thenReturn(Observable.just(movie))
        repository.getMovieObservable(listOf(500))
        Mockito.verify(apiService).getMovie(500)
    }
}